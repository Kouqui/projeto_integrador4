package com.fut.fut360.servidor;

import com.fut.fut360.Model.Atleta;
import com.fut.fut360.Model.Evento;
import com.fut.fut360.comunicados.*;
import com.fut.fut360.rede.Parceiro;
import com.fut.fut360.service.AtletaDAO;
import com.fut.fut360.service.EventoDAO;
import com.fut.fut360.service.PerformanceService;
import com.fut.fut360.service.SystemService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SupervisoraDeConexao extends Thread {

    private Parceiro usuario;
    private Socket conexao;
    private ArrayList<Parceiro> usuarios;

    // Serviços e DAOs (Conexão com Banco e Lógica)
    private SystemService systemService = new SystemService();
    private PerformanceService performanceService = new PerformanceService();
    private AtletaDAO atletaDAO = new AtletaDAO();
    private EventoDAO eventoDAO = new EventoDAO();

    public SupervisoraDeConexao(Socket conexao, ArrayList<Parceiro> usuarios) throws Exception {
        this.conexao = conexao;
        this.usuarios = usuarios;
    }

    public void run() {
        try {
            this.usuario = new Parceiro(this.conexao,
                    new ObjectInputStream(this.conexao.getInputStream()),
                    new ObjectOutputStream(this.conexao.getOutputStream()));

            this.usuarios.add(this.usuario);
            System.out.println("Cliente conectado: " + this.conexao.getInetAddress());

            for (;;) {
                Object objRecebido = this.usuario.envie();
                if (objRecebido == null) return;

                Comunicado comunicado = (Comunicado) objRecebido;

                // --- 1. LOGIN ---
                if (comunicado instanceof PedidoLogin) {
                    systemService.registrarUso("Login");
                    PedidoLogin req = (PedidoLogin) comunicado;
                    if ("adm123".equals(req.getUsuario()) && "123123".equals(req.getSenha())) {
                        this.usuario.receba(new Resultado(1.0, "Login Sucesso!"));
                    } else {
                        this.usuario.receba(new Resultado(0.0, "Acesso Negado"));
                    }
                }

                // --- 2. DASHBOARD (MONITORAMENTO) ---
                else if (comunicado instanceof PedidoEstatisticas) {
                    // Nota: Não registramos uso aqui para o timer automático não poluir o gráfico
                    this.usuario.receba(new RespostaDashboard(
                            systemService.getServerHealth(),
                            systemService.getEstatisticasReais()
                    ));
                }

                // --- 3. LISTAR ATLETAS ---
                else if (comunicado instanceof PedidoListarAtletas) {
                    systemService.registrarUso("Scout");

                    List<Atleta> lista = atletaDAO.listarTodos();

                    // Adiciona análise de performance simulada nos dados reais
                    List<AtletaAnalise> listaAnalisada = lista.stream().map(a -> {
                        double score = (Math.random() * 100);
                        return new AtletaAnalise(
                                a,
                                performanceService.definirFase(score),
                                performanceService.gerarVereditoScout(a, score),
                                "R$ 1.500.000,00"
                        );
                    }).collect(Collectors.toList());

                    this.usuario.receba(new Resultado(listaAnalisada.size(), "Lista SQL OK"));
                    this.usuario.receba(new ArrayList<>(listaAnalisada));
                }

                // --- 4. CADASTRAR ATLETA ---
                else if (comunicado instanceof PedidoCadastrarAtleta) {
                    systemService.registrarUso("Cadastro");
                    PedidoCadastrarAtleta req = (PedidoCadastrarAtleta) comunicado;

                    System.out.println("Tentando salvar atleta: " + req.getAtleta().getNome());

                    boolean salvou = atletaDAO.salvar(req.getAtleta());

                    if (salvou) {
                        this.usuario.receba(new Resultado(1.0, "Salvo no MySQL com Sucesso!"));
                    } else {
                        this.usuario.receba(new Resultado(0.0, "ERRO: O Banco recusou. Verifique o console."));
                    }
                }

                // --- 5. SAIR ---
                else if (comunicado instanceof PedidoParaSair) {
                    this.usuarios.remove(this.usuario);
                    this.usuario.adeus();
                    return;
                }

                // --- 6. LISTAR EVENTOS (AGENDA) ---
                else if (comunicado instanceof PedidoListarEventos) {
                    systemService.registrarUso("Agenda");
                    List<Evento> lista = eventoDAO.listarTodos();

                    this.usuario.receba(new Resultado(lista.size(), "Agenda OK"));
                    this.usuario.receba(new ArrayList<>(lista));
                }

                // --- 7. CADASTRAR EVENTO ---
                else if (comunicado instanceof PedidoCadastrarEvento) {
                    systemService.registrarUso("Cadastro Evento");
                    PedidoCadastrarEvento req = (PedidoCadastrarEvento) comunicado;

                    boolean salvou = eventoDAO.salvar(req.getEvento());
                    if (salvou) {
                        this.usuario.receba(new Resultado(1.0, "Evento Agendado!"));
                    } else {
                        this.usuario.receba(new Resultado(0.0, "Erro ao agendar. Verifique o servidor."));
                    }
                }

                // --- 8. LIMPEZA / OTIMIZAÇÃO ---
                else if (comunicado instanceof PedidoLimparLogs) {
                    systemService.resetarContadores();
                    systemService.liberarMemoria();
                    this.usuario.receba(new Resultado(1.0, "Sistema Otimizado! RAM liberada e Logs zerados."));
                }
            }
        } catch (Exception e) {
            try { this.usuarios.remove(this.usuario); this.conexao.close(); } catch (Exception x) {}
        }
    }
}
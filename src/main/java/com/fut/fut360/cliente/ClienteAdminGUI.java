package com.fut.fut360.cliente;

import com.fut.fut360.Model.Atleta;
import com.fut.fut360.Model.Evento;
import com.fut.fut360.comunicados.*;
import com.fut.fut360.rede.Parceiro;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ClienteAdminGUI {

    private Parceiro servidor;
    private JFrame frameMain;
    private JFrame frameLogin;

    // --- PALETA DE CORES (DARK MODE MODERNO) ---
    private final Color COR_FUNDO = new Color(30, 30, 40);       // Cinza Escuro
    private final Color COR_PAINEL = new Color(40, 40, 55);      // Cinza Azulado
    private final Color COR_DESTAQUE = new Color(60, 140, 240);  // Azul Brilhante
    private final Color COR_TEXTO = new Color(230, 230, 230);    // Branco Suave
    private final Color COR_VERDE = new Color(46, 204, 113);
    private final Color COR_VERMELHO = new Color(231, 76, 60);

    // Fontes
    private final Font FONT_TITULO = new Font("Segoe UI", Font.BOLD, 16);
    private final Font FONT_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);

    // Componentes
    private JLabel lblRam, lblCpu, lblUptime, lblStatus;
    private PainelGrafico painelGrafico;
    private JTable tabelaAtletas;
    private DefaultTableModel modeloTabela;
    private JTable tabelaEventos;
    private DefaultTableModel modeloEventos;

    public static void main(String[] args) {
        // Tenta aplicar o LookAndFeel Nimbus (Mais moderno que o padrão)
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());

                    // Customiza as cores do Nimbus para Dark Mode
                    UIManager.put("control", new Color(30, 30, 40));
                    UIManager.put("text", new Color(230, 230, 230));
                    UIManager.put("nimbusBase", new Color(40, 40, 55));
                    UIManager.put("nimbusFocus", new Color(60, 140, 240));
                    UIManager.put("nimbusLightBackground", new Color(30, 30, 40));
                    UIManager.put("nimbusSelectionBackground", new Color(60, 140, 240));
                    break;
                }
            }
        } catch (Exception e) {}

        SwingUtilities.invokeLater(() -> new ClienteAdminGUI().iniciar());
    }

    public void iniciar() {
        conectarServidor();
        montarTelaLogin();
    }

    private void conectarServidor() {
        try {
            Socket socket = new Socket("localhost", 3000);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            this.servidor = new Parceiro(socket, in, out);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "❌ Servidor Offline!\nVerifique se o Servidor.java está rodando.", "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    // --- TELA DE LOGIN ESTILIZADA ---
    private void montarTelaLogin() {
        frameLogin = new JFrame("Fut360 - Login");
        frameLogin.setSize(400, 350);
        frameLogin.setLocationRelativeTo(null);
        frameLogin.setUndecorated(true); // Remove a barra de título padrão
        frameLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(COR_PAINEL);
        panel.setBorder(BorderFactory.createLineBorder(COR_DESTAQUE, 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitulo = new JLabel("FUT360");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(COR_TEXTO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);

        JTextField txtUser = new JTextField("adm123");
        estilizarCampo(txtUser);

        JPasswordField txtPass = new JPasswordField("123123");
        estilizarCampo(txtPass);

        JButton btnEntrar = criarBotao("ACESSAR SISTEMA", COR_DESTAQUE);
        JButton btnSair = criarBotao("CANCELAR", COR_VERMELHO);

        // Adicionando componentes
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        gbc.gridy = 1; gbc.gridwidth = 2;
        panel.add(new JLabel("Usuário:"), gbc);
        gbc.gridy = 2;
        panel.add(txtUser, gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Senha:"), gbc);
        gbc.gridy = 4;
        panel.add(txtPass, gbc);

        gbc.gridy = 5; gbc.gridwidth = 1;
        panel.add(btnSair, gbc);
        gbc.gridx = 1;
        panel.add(btnEntrar, gbc);

        frameLogin.add(panel);

        // Ações
        btnSair.addActionListener(e -> System.exit(0));
        btnEntrar.addActionListener(e -> {
            try {
                synchronized (servidor) {
                    servidor.receba(new PedidoLogin(txtUser.getText(), new String(txtPass.getPassword())));
                    Resultado res = (Resultado) servidor.envie();
                    if (res.getValorResultante() == 1.0) {
                        frameLogin.dispose();
                        montarDashboardPrincipal();
                    } else {
                        JOptionPane.showMessageDialog(frameLogin, "Senha Inválida", "Acesso Negado", JOptionPane.WARNING_MESSAGE);
                    }
                }
            } catch (Exception ex) { ex.printStackTrace(); }
        });

        frameLogin.setVisible(true);
    }

    // --- DASHBOARD PRINCIPAL ---
    private void montarDashboardPrincipal() {
        frameMain = new JFrame("Fut360 Enterprise - Dashboard Analítico");
        frameMain.setSize(1100, 700);
        frameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMain.setLocationRelativeTo(null);
        frameMain.getContentPane().setBackground(COR_FUNDO);

        JTabbedPane abas = new JTabbedPane();
        abas.setFont(FONT_TITULO);
        abas.setBackground(COR_PAINEL);
        abas.setForeground(Color.WHITE);

        // ------------------------------------------
        // ABA 1: MONITORAMENTO
        // ------------------------------------------
        JPanel abaMonitor = new JPanel(new BorderLayout(15, 15));
        abaMonitor.setBackground(COR_FUNDO);
        abaMonitor.setBorder(new EmptyBorder(15, 15, 15, 15));

        // Cards Topo
        JPanel panelCards = new JPanel(new GridLayout(1, 4, 15, 0));
        panelCards.setBackground(COR_FUNDO);

        lblStatus = criarCard(panelCards, "STATUS SERVIDOR", COR_VERDE);
        lblRam = criarCard(panelCards, "MEMÓRIA RAM", new Color(255, 165, 0)); // Laranja
        lblCpu = criarCard(panelCards, "USO DE CPU", new Color(52, 152, 219)); // Azul
        lblUptime = criarCard(panelCards, "TEMPO ONLINE", Color.LIGHT_GRAY);

        abaMonitor.add(panelCards, BorderLayout.NORTH);

        // Gráfico Central
        painelGrafico = new PainelGrafico();
        painelGrafico.setBackground(COR_PAINEL);
        painelGrafico.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COR_DESTAQUE), " TRÁFEGO EM TEMPO REAL ", 0, 0, FONT_TITULO, COR_TEXTO));

        abaMonitor.add(painelGrafico, BorderLayout.CENTER);

        // Botão Vassoura
        JPanel panelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelAcoes.setBackground(COR_FUNDO);
        JButton btnLimpar = criarBotao(" OTIMIZAR SISTEMA", new Color(241, 196, 15)); // Amarelo Ouro
        btnLimpar.setForeground(Color.BLACK); // Texto preto para contraste no amarelo

        btnLimpar.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(frameMain, "Zerar logs e limpar RAM?", "Otimização", JOptionPane.YES_NO_OPTION) == 0) {
                enviarComando(new PedidoLimparLogs(), null);
                atualizarMonitoramento();
            }
        });
        panelAcoes.add(btnLimpar);
        abaMonitor.add(panelAcoes, BorderLayout.SOUTH);

        abas.addTab(" MONITORAMENTO  ", abaMonitor);

        // ------------------------------------------
        // ABA 2: SCOUT (ATLETAS)
        // ------------------------------------------
        JPanel abaScout = new JPanel(new BorderLayout(10, 10));
        abaScout.setBackground(COR_FUNDO);
        abaScout.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] colunas = {"Nome", "Posição", "Idade", "Valor Estimado", "Fase Atual", "Veredito"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabelaAtletas = new JTable(modeloTabela);
        estilizarTabela(tabelaAtletas);

        JPanel panelBotoesScout = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBotoesScout.setBackground(COR_FUNDO);
        JButton btnAtualizarLista = criarBotao(" ATUALIZAR LISTA", COR_DESTAQUE);
        JButton btnNovoJogador = criarBotao(" NOVO JOGADOR", COR_VERDE);

        panelBotoesScout.add(btnAtualizarLista);
        panelBotoesScout.add(btnNovoJogador);

        abaScout.add(new JScrollPane(tabelaAtletas), BorderLayout.CENTER);
        abaScout.add(panelBotoesScout, BorderLayout.NORTH);

        abas.addTab("  SCOUT & ANÁLISE  ", abaScout);

        // ------------------------------------------
        // ABA 3: AGENDA (EVENTOS)
        // ------------------------------------------
        JPanel abaEventos = new JPanel(new BorderLayout(10, 10));
        abaEventos.setBackground(COR_FUNDO);
        abaEventos.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] colEventos = {"Data", "Hora", "Tipo", "Título", "Local", "Categoria"};
        modeloEventos = new DefaultTableModel(colEventos, 0);
        tabelaEventos = new JTable(modeloEventos);
        estilizarTabela(tabelaEventos);

        JPanel panelBtnEventos = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBtnEventos.setBackground(COR_FUNDO);
        JButton btnAtualizarEventos = criarBotao(" ATUALIZAR AGENDA", COR_DESTAQUE);
        JButton btnNovoEvento = criarBotao(" AGENDAR EVENTO", COR_VERDE);

        panelBtnEventos.add(btnAtualizarEventos);
        panelBtnEventos.add(btnNovoEvento);

        abaEventos.add(new JScrollPane(tabelaEventos), BorderLayout.CENTER);
        abaEventos.add(panelBtnEventos, BorderLayout.NORTH);

        abas.addTab("  AGENDA & EVENTOS  ", abaEventos);

        // ------------------------------------------
        // RODAPÉ: BOTÃO SAIR
        // ------------------------------------------
        JButton btnSair = criarBotao("DESCONECTAR SISTEMA", COR_VERMELHO);
        btnSair.addActionListener(e -> {
            enviarComando(new PedidoParaSair(), null);
            System.exit(0);
        });

        frameMain.add(abas, BorderLayout.CENTER);
        frameMain.add(btnSair, BorderLayout.SOUTH);
        frameMain.setVisible(true);

        // --- LÓGICA DE EVENTOS E TIMERS ---
        new Timer().scheduleAtFixedRate(new TimerTask() {
            public void run() { atualizarMonitoramento(); }
        }, 0, 1500);

        btnAtualizarLista.addActionListener(e -> atualizarTabelaAtletas());
        btnAtualizarEventos.addActionListener(e -> atualizarTabelaEventos());

        btnNovoJogador.addActionListener(e -> abrirFormularioJogador());
        btnNovoEvento.addActionListener(e -> abrirFormularioEvento());
    }

    // --- MÉTODOS AUXILIARES DE DESIGN ---

    private JButton criarBotao(String texto, Color corFundo) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setBackground(corFundo);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding interno
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito Hover simples
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(corFundo.brighter()); }
            public void mouseExited(MouseEvent e) { btn.setBackground(corFundo); }
        });
        return btn;
    }

    private void estilizarCampo(JTextField campo) {
        campo.setFont(FONT_NORMAL);
        campo.setBackground(new Color(50, 50, 65));
        campo.setForeground(Color.WHITE);
        campo.setCaretColor(Color.WHITE);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 120)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
    }

    private void estilizarTabela(JTable tabela) {
        tabela.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabela.setRowHeight(30); // Linhas mais altas para respirar
        tabela.setBackground(new Color(45, 45, 60));
        tabela.setForeground(Color.WHITE);
        tabela.setGridColor(new Color(60, 60, 80));
        tabela.setSelectionBackground(COR_DESTAQUE);
        tabela.setSelectionForeground(Color.WHITE);

        JTableHeader header = tabela.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(30, 30, 40));
        header.setForeground(COR_DESTAQUE);
        header.setOpaque(false); // Necessário para pintar cor customizada no Nimbus

        // Centralizar texto nas células
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for(int i=0; i<tabela.getColumnCount(); i++){
            tabela.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }

    private JLabel criarCard(JPanel pai, String titulo, Color corBorda) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(COR_PAINEL);
        // Borda colorida embaixo para dar estilo
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, corBorda));

        JLabel lTit = new JLabel(titulo);
        lTit.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lTit.setForeground(new Color(150, 150, 160)); // Cinza claro
        lTit.setHorizontalAlignment(SwingConstants.CENTER);
        lTit.setBorder(new EmptyBorder(10, 0, 0, 0));

        JLabel lVal = new JLabel("...");
        lVal.setForeground(Color.WHITE);
        lVal.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lVal.setHorizontalAlignment(SwingConstants.CENTER);
        lVal.setBorder(new EmptyBorder(5, 0, 15, 0));

        p.add(lTit, BorderLayout.NORTH);
        p.add(lVal, BorderLayout.CENTER);
        pai.add(p);
        return lVal;
    }

    // --- COMUNICAÇÃO E LÓGICA ---

    private void enviarComando(Object pedido, Runnable callback) {
        if (servidor == null) return;
        new Thread(() -> { // Thread separada para não travar a UI
            synchronized (servidor) {
                try {
                    servidor.receba(pedido);
                    if(callback != null) callback.run(); // Callback se precisar ler retorno customizado
                    else servidor.envie(); // Consome retorno padrão se não houver callback
                } catch (Exception e) { e.printStackTrace(); }
            }
        }).start();
    }

    private void atualizarMonitoramento() {
        if (servidor == null) return;
        synchronized (servidor) {
            try {
                servidor.receba(new PedidoEstatisticas());
                Object obj = servidor.envie();
                if (obj instanceof RespostaDashboard) {
                    RespostaDashboard res = (RespostaDashboard) obj;
                    Map<String, Object> h = res.getHardwareStats();
                    SwingUtilities.invokeLater(() -> {
                        lblStatus.setText(h.get("status").toString());
                        lblRam.setText(h.get("ramUsed") + " MB");
                        lblCpu.setText(String.format("%.1f %%", (Double)h.get("cpuRaw")));
                        lblUptime.setText(h.get("uptime").toString());
                        painelGrafico.setDados(res.getAcessosPaginas());
                    });
                }
            } catch (Exception e) {}
        }
    }

    private void atualizarTabelaAtletas() {
        if (servidor == null) return;
        synchronized (servidor) {
            try {
                servidor.receba(new PedidoListarAtletas());
                servidor.envie(); // Consome msg de qtd
                Object resp = servidor.envie(); // Lista
                if (resp instanceof ArrayList) {
                    ArrayList<AtletaAnalise> lista = (ArrayList<AtletaAnalise>) resp;
                    SwingUtilities.invokeLater(() -> {
                        modeloTabela.setRowCount(0);
                        for (AtletaAnalise a : lista) {
                            modeloTabela.addRow(new Object[]{
                                    a.getAtleta().getNome(), a.getAtleta().getPosicao(),
                                    a.getAtleta().getIdade(), a.getValorMercadoEstimado(),
                                    a.getFase(), a.getVeredito()
                            });
                        }
                    });
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private void atualizarTabelaEventos() {
        if (servidor == null) return;
        synchronized (servidor) {
            try {
                servidor.receba(new PedidoListarEventos());
                servidor.envie();
                Object resp = servidor.envie();
                if (resp instanceof ArrayList) {
                    ArrayList<Evento> lista = (ArrayList<Evento>) resp;
                    SwingUtilities.invokeLater(() -> {
                        modeloEventos.setRowCount(0);
                        for (Evento evt : lista) {
                            modeloEventos.addRow(new Object[]{
                                    evt.getData(), evt.getHoraInicio(), evt.getTipo(),
                                    evt.getTitulo(), evt.getLocal(), evt.getCategoria()
                            });
                        }
                    });
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private void abrirFormularioJogador() {
        // Design do Popup também precisa ser dark? Sim, mas o JOptionPane pega o tema padrão.
        // Vamos apenas montar o painel.
        JPanel p = new JPanel(new GridLayout(0, 2, 10, 10));
        JTextField tNome = new JTextField();
        JTextField tPos = new JTextField();
        JTextField tIdade = new JTextField();
        JTextField tNac = new JTextField("Brasil");
        JTextField tAlt = new JTextField();
        JTextField tPeso = new JTextField();
        JComboBox<String> cPe = new JComboBox<>(new String[]{"Destro", "Canhoto", "Ambidestro"});
        JComboBox<String> cCat = new JComboBox<>(new String[]{"Profissional", "Sub-20", "Sub-17"});

        p.add(new JLabel("Nome:")); p.add(tNome);
        p.add(new JLabel("Posição:")); p.add(tPos);
        p.add(new JLabel("Idade:")); p.add(tIdade);
        p.add(new JLabel("Nacionalidade:")); p.add(tNac);
        p.add(new JLabel("Altura (cm):")); p.add(tAlt);
        p.add(new JLabel("Peso (kg):")); p.add(tPeso);
        p.add(new JLabel("Pé Dominante:")); p.add(cPe);
        p.add(new JLabel("Categoria:")); p.add(cCat);

        if (JOptionPane.showConfirmDialog(frameMain, p, "Novo Jogador", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                Atleta novo = new Atleta(
                        tNome.getText(), tPos.getText(), Integer.parseInt(tIdade.getText()), 1500000.0,
                        tNac.getText(), Integer.parseInt(tAlt.getText()), Double.parseDouble(tPeso.getText().replace(",",".")),
                        (String)cPe.getSelectedItem(), (String)cCat.getSelectedItem()
                );
                synchronized(servidor) {
                    servidor.receba(new PedidoCadastrarAtleta(novo));
                    Object r = servidor.envie();
                    if(r instanceof Resultado) {
                        JOptionPane.showMessageDialog(frameMain, ((Resultado)r).getMensagem());
                        atualizarTabelaAtletas();
                    }
                }
            } catch (Exception x) { JOptionPane.showMessageDialog(frameMain, "Erro nos dados: " + x.getMessage()); }
        }
    }

    private void abrirFormularioEvento() {
        JPanel p = new JPanel(new GridLayout(0, 2, 5, 5));
        JTextField tTitulo = new JTextField();
        JTextField tData = new JTextField("2025-10-20");
        JTextField tInicio = new JTextField("14:00");
        JTextField tFim = new JTextField("16:00");
        JTextField tLocal = new JTextField();
        JTextField tAdv = new JTextField();
        JTextField tDesc = new JTextField();
        JComboBox<String> cTipo = new JComboBox<>(new String[]{"TREINO", "JOGO", "REUNIAO"});
        JComboBox<String> cCat = new JComboBox<>(new String[]{"Profissional", "Sub-20"});

        p.add(new JLabel("Título:")); p.add(tTitulo);
        p.add(new JLabel("Tipo:")); p.add(cTipo);
        p.add(new JLabel("Data:")); p.add(tData);
        p.add(new JLabel("Início:")); p.add(tInicio);
        p.add(new JLabel("Fim:")); p.add(tFim);
        p.add(new JLabel("Local:")); p.add(tLocal);
        p.add(new JLabel("Adversário:")); p.add(tAdv);
        p.add(new JLabel("Categoria:")); p.add(cCat);
        p.add(new JLabel("Descrição:")); p.add(tDesc);

        if (JOptionPane.showConfirmDialog(frameMain, p, "Novo Evento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            try {
                Evento novo = new Evento(tTitulo.getText(), tData.getText(), tInicio.getText(), tFim.getText(),
                        (String)cTipo.getSelectedItem(), (String)cCat.getSelectedItem(), tLocal.getText(), tAdv.getText(), tDesc.getText());
                synchronized(servidor) {
                    servidor.receba(new PedidoCadastrarEvento(novo));
                    Object r = servidor.envie();
                    if(r instanceof Resultado) {
                        JOptionPane.showMessageDialog(frameMain, ((Resultado)r).getMensagem());
                        atualizarTabelaEventos();
                    }
                }
            } catch (Exception x) { x.printStackTrace(); }
        }
    }

    // --- GRÁFICO CUSTOMIZADO (MANTIDO E ADAPTADO) ---
    class PainelGrafico extends JPanel {
        private Map<String, Integer> dados;
        public void setDados(Map<String, Integer> dados) { this.dados = dados; repaint(); }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (dados == null || dados.isEmpty()) return;
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Suaviza as bordas

            int w = getWidth(), h = getHeight();
            int barraW = 60, espaco = 40, x = 60;
            int maxVal = dados.values().stream().max(Integer::compare).orElse(1);

            g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
            int i = 0;
            // Cores neon para o gráfico
            Color[] cores = {new Color(0, 255, 255), new Color(255, 0, 255), new Color(255, 200, 0), new Color(0, 255, 0), new Color(255, 100, 100)};

            for (Map.Entry<String, Integer> entry : dados.entrySet()) {
                int val = entry.getValue();
                int barraH = (int) (((double) val / maxVal) * (h - 80));

                // Barra Sólida
                g2.setColor(cores[i % cores.length]);
                g2.fillRoundRect(x, h - barraH - 40, barraW, barraH, 10, 10); // Bordas arredondadas

                // Texto Valor (Topo)
                g2.setColor(Color.WHITE);
                g2.drawString(String.valueOf(val), x + 20, h - barraH - 45);

                // Texto Categoria (Base)
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawString(entry.getKey(), x, h - 20);

                x += barraW + espaco;
                i++;
            }
        }
    }
}
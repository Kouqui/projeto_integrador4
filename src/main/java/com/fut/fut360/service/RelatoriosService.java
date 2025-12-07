package com.fut.fut360.service;

import com.fut.fut360.Model.Evento;
import com.fut.fut360.Model.EventoEstatistica;
import com.fut.fut360.Model.Transaction;
import com.fut.fut360.Repository.EventoEstatisticaRepository;
import com.fut.fut360.Repository.EventoRepository;
import com.fut.fut360.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class RelatoriosService {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoEstatisticaRepository eventoEstatisticaRepository;

    public byte[] gerarRelatorioFluxoCaixa(String mes, String ano) {
        int mesInt = Integer.parseInt(mes);
        int anoInt = Integer.parseInt(ano);

        List<Transaction> todasTransacoes = transactionService.findAll();
        
        // DEBUG: Verificar quantas transações foram encontradas
        System.out.println("========================================");
        System.out.println("DEBUG - Total de transações no banco: " + todasTransacoes.size());
        System.out.println("DEBUG - Buscando por: Mês=" + mesInt + ", Ano=" + anoInt);
        
        // DEBUG: Mostrar todas as datas das transações
        for (Transaction t : todasTransacoes) {
            System.out.println("DEBUG - Transação: " + t.getDescription() + " | Data: " + t.getDate() + " | Tipo: " + t.getType() + " | Valor: " + t.getValue());
        }
        System.out.println("========================================");

        List<Transaction> transacoesMes = todasTransacoes.stream()
                .filter(t -> {
                    LocalDate data = t.getDate();
                    return data != null && data.getMonthValue() == mesInt && data.getYear() == anoInt;
                })
                .toList();

        BigDecimal receitasMes = BigDecimal.ZERO;
        BigDecimal despesasMes = BigDecimal.ZERO;

        StringBuilder detalhamento = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        detalhamento.append("\n=== RECEITAS DO PERÍODO ===\n\n");
        for (Transaction t : transacoesMes) {
            if ("receita".equalsIgnoreCase(t.getType())) {
                receitasMes = receitasMes.add(t.getValue());
                detalhamento.append(String.format("%-12s | %-30s | R$ %,12.2f | %s\n",
                        t.getDate().format(formatter),
                        t.getDescription(),
                        t.getValue(),
                        t.getCategory()));
            }
        }

        detalhamento.append("\n=== DESPESAS DO PERÍODO ===\n\n");
        for (Transaction t : transacoesMes) {
            if ("despesa".equalsIgnoreCase(t.getType())) {
                despesasMes = despesasMes.add(t.getValue());
                detalhamento.append(String.format("%-12s | %-30s | R$ %,12.2f | %s\n",
                        t.getDate().format(formatter),
                        t.getDescription(),
                        t.getValue(),
                        t.getCategory()));
            }
        }

        BigDecimal saldoMes = receitasMes.subtract(despesasMes);
        String nomeMes = LocalDate.of(anoInt, mesInt, 1)
                .getMonth()
                .getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));

        String conteudo = """
                ╔═══════════════════════════════════════════════════════════════════╗
                ║         RELATÓRIO DE FLUXO DE CAIXA - FUT360                      ║
                ╚═══════════════════════════════════════════════════════════════════╝

                Período: %s/%s
                Data de Geração: %s

                ═══════════════════════════════════════════════════════════════════
                                         RESUMO MENSAL
                ═══════════════════════════════════════════════════════════════════

                Total de Receitas:    R$ %,15.2f
                Total de Despesas:    R$ %,15.2f
                ───────────────────────────────────────────────────────────────────
                Saldo do Período:     R$ %,15.2f

                ═══════════════════════════════════════════════════════════════════
                                    DETALHAMENTO DE MOVIMENTAÇÕES
                ═══════════════════════════════════════════════════════════════════
                %s

                ═══════════════════════════════════════════════════════════════════
                Quantidade de transações no período: %d
                ═══════════════════════════════════════════════════════════════════
                """.formatted(
                nomeMes.toUpperCase(),
                ano,
                LocalDate.now().format(formatter),
                receitasMes,
                despesasMes,
                saldoMes,
                detalhamento.toString(),
                transacoesMes.size()
        );

        return PdfGenerator.gerarPdfSimples(conteudo);
    }

    public byte[] gerarRelatorioCategoria(String categoria) {
        List<Transaction> todasTransacoes = transactionService.findAll();

        List<Transaction> transacoesFiltradas;
        if ("Todas".equalsIgnoreCase(categoria)) {
            transacoesFiltradas = todasTransacoes;
        } else {
            transacoesFiltradas = todasTransacoes.stream()
                    .filter(t -> t.getCategory() != null && t.getCategory().equalsIgnoreCase(categoria))
                    .toList();
        }

        StringBuilder detalhamento = new StringBuilder();
        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        detalhamento.append("\n=== RECEITAS ===\n\n");
        for (Transaction t : transacoesFiltradas) {
            if ("receita".equalsIgnoreCase(t.getType())) {
                totalReceitas = totalReceitas.add(t.getValue());
                detalhamento.append(String.format("%-12s | %-35s | R$ %,12.2f\n",
                        t.getDate().format(formatter),
                        t.getDescription(),
                        t.getValue()));
            }
        }

        detalhamento.append("\n=== DESPESAS ===\n\n");
        for (Transaction t : transacoesFiltradas) {
            if ("despesa".equalsIgnoreCase(t.getType())) {
                totalDespesas = totalDespesas.add(t.getValue());
                detalhamento.append(String.format("%-12s | %-35s | R$ %,12.2f\n",
                        t.getDate().format(formatter),
                        t.getDescription(),
                        t.getValue()));
            }
        }

        BigDecimal saldo = totalReceitas.subtract(totalDespesas);

        String conteudo = """
                ╔═══════════════════════════════════════════════════════════════════╗
                ║         RELATÓRIO POR CATEGORIA - FUT360                          ║
                ╚═══════════════════════════════════════════════════════════════════╝

                Categoria: %s
                Data de Geração: %s

                ═══════════════════════════════════════════════════════════════════
                                         RESUMO DA CATEGORIA
                ═══════════════════════════════════════════════════════════════════

                Total de Receitas:    R$ %,15.2f
                Total de Despesas:    R$ %,15.2f
                ───────────────────────────────────────────────────────────────────
                Saldo (Receitas - Despesas):  R$ %,15.2f

                ═══════════════════════════════════════════════════════════════════
                                    DETALHAMENTO DE TRANSAÇÕES
                ═══════════════════════════════════════════════════════════════════
                %s

                ═══════════════════════════════════════════════════════════════════
                Quantidade de transações encontradas: %d
                ═══════════════════════════════════════════════════════════════════
                """.formatted(
                categoria,
                LocalDate.now().format(formatter),
                totalReceitas,
                totalDespesas,
                saldo,
                detalhamento.toString(),
                transacoesFiltradas.size()
        );

        return PdfGenerator.gerarPdfSimples(conteudo);
    }

    public byte[] gerarRelatorioPosJogo(Long eventoId) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        // Busca o evento
        Optional<Evento> eventoOpt = eventoRepository.findById(eventoId);
        if (eventoOpt.isEmpty()) {
            String erro = """
                    ╔═══════════════════════════════════════════════════════════════════╗
                    ║         RELATÓRIO TÉCNICO PÓS-JOGO - FUT360                       ║
                    ╚═══════════════════════════════════════════════════════════════════╝

                    ERRO: Evento não encontrado com ID: %d
                    Data de Geração: %s
                    """.formatted(eventoId, LocalDate.now().format(dateFormatter));
            return PdfGenerator.gerarPdfSimples(erro);
        }

        Evento evento = eventoOpt.get();

        // Busca as estatísticas do evento
        Optional<EventoEstatistica> estatisticasOpt = eventoEstatisticaRepository.findByEventId(eventoId);

        String conteudo;

        if (estatisticasOpt.isEmpty()) {
            // Caso não existam estatísticas cadastradas
            conteudo = """
                    ╔═══════════════════════════════════════════════════════════════════╗
                    ║         RELATÓRIO TÉCNICO PÓS-JOGO - FUT360                       ║
                    ╚═══════════════════════════════════════════════════════════════════╝

                    Partida: %s
                    Data: %s
                    Horário: %s - %s
                    Local: %s
                    Adversário: %s
                    Data de Geração: %s

                    ═══════════════════════════════════════════════════════════════════
                                        ESTATÍSTICAS DA PARTIDA
                    ═══════════════════════════════════════════════════════════════════

                    ATENÇÃO: As estatísticas desta partida ainda não foram cadastradas
                    no sistema.

                    ═══════════════════════════════════════════════════════════════════
                                              OBSERVAÇÕES
                    ═══════════════════════════════════════════════════════════════════

                    %s

                    ═══════════════════════════════════════════════════════════════════
                    """.formatted(
                    evento.getTitulo(),
                    evento.getDataEvento().format(dateFormatter),
                    evento.getHoraInicio() != null ? evento.getHoraInicio().format(timeFormatter) : "N/A",
                    evento.getHoraFim() != null ? evento.getHoraFim().format(timeFormatter) : "N/A",
                    evento.getLocal() != null ? evento.getLocal() : "N/A",
                    evento.getAdversario() != null ? evento.getAdversario() : "N/A",
                    LocalDate.now().format(dateFormatter),
                    evento.getDescricao() != null ? evento.getDescricao() : "Sem observações"
            );
        } else {
            // Caso existam estatísticas cadastradas
            EventoEstatistica stats = estatisticasOpt.get();

            conteudo = """
                    ╔═══════════════════════════════════════════════════════════════════╗
                    ║         RELATÓRIO TÉCNICO PÓS-JOGO - FUT360                       ║
                    ╚═══════════════════════════════════════════════════════════════════╝

                    Partida: %s
                    Data: %s
                    Horário: %s - %s
                    Local: %s
                    Adversário: %s
                    Data de Geração: %s

                    ═══════════════════════════════════════════════════════════════════
                                        ESTATÍSTICAS DA PARTIDA
                    ═══════════════════════════════════════════════════════════════════

                                                  Time Casa    x    Time Adversário
                    ───────────────────────────────────────────────────────────────────
                    Posse de Bola:                  %s          %s
                    Chutes a Gol:                   %d          %d
                    Escanteios:                     %d          %d
                    Faltas:                         %d          %d
                    Cartões Amarelos:               %d          %d
                    Cartões Vermelhos:              %d          %d

                    ═══════════════════════════════════════════════════════════════════
                                             RESULTADO FINAL
                    ═══════════════════════════════════════════════════════════════════

                                        %s    %d  x  %d    %s

                    ═══════════════════════════════════════════════════════════════════
                                              OBSERVAÇÕES
                    ═══════════════════════════════════════════════════════════════════

                    %s

                    ═══════════════════════════════════════════════════════════════════
                    """.formatted(
                    evento.getTitulo(),
                    evento.getDataEvento().format(dateFormatter),
                    evento.getHoraInicio() != null ? evento.getHoraInicio().format(timeFormatter) : "N/A",
                    evento.getHoraFim() != null ? evento.getHoraFim().format(timeFormatter) : "N/A",
                    evento.getLocal() != null ? evento.getLocal() : "N/A",
                    evento.getAdversario() != null ? evento.getAdversario() : "N/A",
                    LocalDate.now().format(dateFormatter),
                    stats.getPosseBolaTime1() != null ? stats.getPosseBolaTime1() : "N/A",
                    stats.getPosseBolaTime2() != null ? stats.getPosseBolaTime2() : "N/A",
                    stats.getChutesAGolTime1() != null ? stats.getChutesAGolTime1() : 0,
                    stats.getChutesAGolTime2() != null ? stats.getChutesAGolTime2() : 0,
                    stats.getEscanteiosTime1() != null ? stats.getEscanteiosTime1() : 0,
                    stats.getEscanteiosTime2() != null ? stats.getEscanteiosTime2() : 0,
                    stats.getFaltasTime1() != null ? stats.getFaltasTime1() : 0,
                    stats.getFaltasTime2() != null ? stats.getFaltasTime2() : 0,
                    stats.getCartoesAmarelosTime1() != null ? stats.getCartoesAmarelosTime1() : 0,
                    stats.getCartoesAmarelosTime2() != null ? stats.getCartoesAmarelosTime2() : 0,
                    stats.getCartoesVermelhosTime1() != null ? stats.getCartoesVermelhosTime1() : 0,
                    stats.getCartoesVermelhosTime2() != null ? stats.getCartoesVermelhosTime2() : 0,
                    "FUT360",
                    stats.getGolsTime1() != null ? stats.getGolsTime1() : 0,
                    stats.getGolsTime2() != null ? stats.getGolsTime2() : 0,
                    evento.getAdversario() != null ? evento.getAdversario() : "Adversário",
                    evento.getDescricao() != null ? evento.getDescricao() : "Sem observações"
            );
        }

        return PdfGenerator.gerarPdfSimples(conteudo);
    }
}

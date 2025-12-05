package com.fut.fut360.service;

import com.fut.fut360.model.Transaction;
import com.fut.fut360.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Service
public class RelatoriosService {

    @Autowired
    private TransactionService transactionService;

    /**
     * Gera um relatório de Fluxo de Caixa para um mês e ano específicos.
     * Mostra todas as receitas e despesas do período, com saldo final.
     */
    public byte[] gerarRelatorioFluxoCaixa(String mes, String ano) {
        int mesInt = Integer.parseInt(mes);
        int anoInt = Integer.parseInt(ano);

        // Busca todas as transações
        List<Transaction> todasTransacoes = transactionService.findAll();

        // Filtra transações do mês específico
        List<Transaction> transacoesMes = todasTransacoes.stream()
                .filter(t -> {
                    LocalDate data = t.getDate();
                    return data != null && data.getMonthValue() == mesInt && data.getYear() == anoInt;
                })
                .toList();

        // Calcula totais do mês
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

    /**
     * Gera relatório filtrando por categoria específica.
     * Mostra todas as transações (receitas e despesas) dessa categoria.
     */
    public byte[] gerarRelatorioCategoria(String categoria) {
        List<Transaction> todasTransacoes = transactionService.findAll();

        // Filtra por categoria (case insensitive) ou "Todas"
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

    /**
     * Relatório Pós-Jogo (mantido simulado pois não há dados de jogos no sistema).
     */
    public byte[] gerarRelatorioPosJogo(String jogo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String conteudo = """
                ╔═══════════════════════════════════════════════════════════════════╗
                ║         RELATÓRIO TÉCNICO PÓS-JOGO - FUT360                       ║
                ╚═══════════════════════════════════════════════════════════════════╝

                Partida: %s
                Data de Geração: %s

                ═══════════════════════════════════════════════════════════════════
                                    ESTATÍSTICAS DA PARTIDA
                ═══════════════════════════════════════════════════════════════════

                Posse de Bola:         55%%
                Chutes a Gol:          12
                Escanteios:            8
                Faltas:                14
                Cartões Amarelos:      2
                Cartões Vermelhos:     0

                ═══════════════════════════════════════════════════════════════════
                                         RESULTADO FINAL
                ═══════════════════════════════════════════════════════════════════

                Fut360 FC  2  x  1  Adversário FC

                ═══════════════════════════════════════════════════════════════════
                                          OBSERVAÇÕES
                ═══════════════════════════════════════════════════════════════════

                Boa atuação da equipe no segundo tempo.
                Destaque para o meio-campo que controlou o jogo.

                ───────────────────────────────────────────────────────────────────
                NOTA: Este relatório contém dados simulados. A integração com o
                sistema de gerenciamento de jogos está em desenvolvimento.
                ═══════════════════════════════════════════════════════════════════
                """.formatted(jogo, LocalDate.now().format(formatter));

        return PdfGenerator.gerarPdfSimples(conteudo);
    }
}

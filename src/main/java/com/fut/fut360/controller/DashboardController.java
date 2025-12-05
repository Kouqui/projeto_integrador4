//ARQUIVO AUTORIA: Kauã Kouqui, Guilherme Braz (24019760)
package com.fut.fut360.controller;

import com.fut.fut360.Repository.AtletaRepository;
import com.fut.fut360.Repository.EventoRepository;
import com.fut.fut360.service.TransactionService;
import com.fut.fut360.service.PayrollService;
import com.fut.fut360.Model.Atleta;
import com.fut.fut360.Model.Evento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private AtletaRepository atletaRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @GetMapping("/dashboard")
    public String exibirDashboard(Model model) {
        // 1. DADOS FINANCEIROS
        Map<String, BigDecimal> saldoFinanceiro = transactionService.calculateSummary();
        model.addAttribute("saldoAtual", saldoFinanceiro.get("saldoAtual"));
        model.addAttribute("totalReceitas", saldoFinanceiro.get("totalReceitas"));
        model.addAttribute("totalDespesas", saldoFinanceiro.get("totalDespesas"));

        // 2. FOLHA SALARIAL
        Map<String, BigDecimal> resumoFolha = payrollService.calculatePayrollSummary();
        model.addAttribute("folhaSalarial", resumoFolha.get("totalCost"));
        model.addAttribute("folhaPendente", resumoFolha.get("totalPending"));

        // Calcular dias até próximo vencimento (simulado - 5 dias)
        LocalDate proximoVencimento = LocalDate.now().plusDays(5);
        long diasAteVencimento = ChronoUnit.DAYS.between(LocalDate.now(), proximoVencimento);
        model.addAttribute("diasAteVencimento", diasAteVencimento);

        // 3. PRÓXIMOS EVENTOS
        List<Evento> proximosEventos = eventoRepository.findAll()
                .stream()
                .filter(e -> e.getDataEvento() != null && !e.getDataEvento().isBefore(LocalDate.now()))
                .sorted((e1, e2) -> e1.getDataEvento().compareTo(e2.getDataEvento()))
                .limit(3)
                .collect(Collectors.toList());
        model.addAttribute("proximosEventos", proximosEventos);

        // 4. DESEMPENHO DOS ATLETAS (Top 5 com melhor nota de treino)
        List<Atleta> topAtletas = atletaRepository.findAll()
                .stream()
                .filter(a -> !a.getTreinos().isEmpty())
                .sorted((a1, a2) -> {
                    BigDecimal nota1 = a1.getTreinos().get(0).getTreinoNotaGeral();
                    BigDecimal nota2 = a2.getTreinos().get(0).getTreinoNotaGeral();
                    return nota2.compareTo(nota1);
                })
                .limit(5)
                .collect(Collectors.toList());
        model.addAttribute("topAtletas", topAtletas);

        return "dashboard";
    }
}

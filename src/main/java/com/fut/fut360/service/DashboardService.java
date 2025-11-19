package com.fut.fut360.service;

import com.fut.fut360.dto.DashboardDTO;
import com.fut.fut360.model.Atleta;
import com.fut.fut360.Model.Calendario;
import com.fut.fut360.model.PayrollItem;
import com.fut.fut360.model.Transaction;
import com.fut.fut360.repository.AtletaRepository;
import com.fut.fut360.repository.CalendarioRepository;
import com.fut.fut360.repository.PayrollRepository;
import com.fut.fut360.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    @Autowired
    private AtletaRepository atletaRepository;

    @Autowired
    private CalendarioRepository calendarioRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private PayrollRepository payrollRepository;

    /**
     * Retorna todos os dados necessários para o Dashboard
     */
    public DashboardDTO getDashboardData() {
        DashboardDTO.FinanceiroResumo financeiroResumo = getFinanceiroResumo();
        List<DashboardDTO.EventoResumo> proximosEventos = getProximosEventos();
        List<DashboardDTO.AtletaDesempenho> desempenhoAtletas = getDesempenhoAtletas();

        return new DashboardDTO(financeiroResumo, proximosEventos, desempenhoAtletas);
    }

    /**
     * Calcula o resumo financeiro: saldo em caixa e folha salarial
     */
    private DashboardDTO.FinanceiroResumo getFinanceiroResumo() {
        // TODO: Substituir por dados reais do banco
        // Por enquanto, usando dados mockados

        BigDecimal saldoCaixa = calcularSaldoCaixa();
        String percentualComparacao = "+5.2%"; // Comparação com mês anterior
        BigDecimal folhaSalarial = calcularFolhaSalarial();
        String infoVencimento = calcularInfoVencimento();

        return new DashboardDTO.FinanceiroResumo(saldoCaixa, percentualComparacao, folhaSalarial, infoVencimento);
    }

    /**
     * Calcula o saldo em caixa baseado nas transações
     */
    private BigDecimal calcularSaldoCaixa() {
        return transactionRepository.calcularSaldo();
    }

    /**
     * Calcula o total da folha salarial do mês
     */
    private BigDecimal calcularFolhaSalarial() {
        LocalDate hoje = LocalDate.now();
        LocalDate inicioMes = hoje.withDayOfMonth(1);
        LocalDate fimMes = hoje.withDayOfMonth(hoje.lengthOfMonth());

        return payrollRepository.calcularTotalFolhaMes(inicioMes, fimMes);
    }

    /**
     * Calcula informação sobre vencimento da folha
     */
    private String calcularInfoVencimento() {
        PayrollItem proximoPagamento = payrollRepository.findFirstByStatusOrderByPaymentDateAsc("pendente");

        if (proximoPagamento == null) {
            return "Nenhum pagamento pendente";
        }

        LocalDate hoje = LocalDate.now();
        LocalDate vencimento = proximoPagamento.getPaymentDate();
        long diasAteVencimento = ChronoUnit.DAYS.between(hoje, vencimento);

        if (diasAteVencimento == 0) {
            return "Vence hoje";
        } else if (diasAteVencimento == 1) {
            return "Vencimento em 1 dia";
        } else if (diasAteVencimento > 0) {
            return "Vencimento em " + diasAteVencimento + " dias";
        } else {
            return "Vencido há " + Math.abs(diasAteVencimento) + " dias";
        }
    }

    /**
     * Busca os próximos eventos do calendário
     */
    private List<DashboardDTO.EventoResumo> getProximosEventos() {
        LocalDateTime agora = LocalDateTime.now();
        List<Calendario> eventos = calendarioRepository.findTop5ByDataHoraAfterOrderByDataHoraAsc(agora);

        return eventos.stream().map(evento -> {
            String dia = evento.getDataHora().format(DateTimeFormatter.ofPattern("dd"));
            String mes = evento.getDataHora().format(DateTimeFormatter.ofPattern("MMM", Locale.of("pt", "BR"))).toUpperCase();

            return new DashboardDTO.EventoResumo(
                dia,
                mes,
                evento.getTitulo(),
                evento.getDescricao(),
                mapCategoria(evento.getCategoria())
            );
        }).collect(Collectors.toList());
    }

    /**
     * Mapeia categoria do evento para classe CSS
     */
    private String mapCategoria(String categoria) {
        if (categoria == null) return "general";
        return switch (categoria.toLowerCase()) {
            case "profissional" -> "professional";
            case "sub-17" -> "sub17";
            case "sub-20" -> "sub20";
            default -> "general";
        };
    }

    /**
     * Busca os atletas com melhor desempenho recente no treino
     */
    private List<DashboardDTO.AtletaDesempenho> getDesempenhoAtletas() {
        List<Atleta> atletas = atletaRepository.findTop5ByOrderByTreinoNotaGeralDesc();

        return atletas.stream().map(atleta -> {
            int desempenhoFisico = calcularDesempenhoFisico(atleta);
            String tendencia = calcularTendencia(atleta);

            return new DashboardDTO.AtletaDesempenho(
                atleta.getName(),
                atleta.getCategory(),
                atleta.getPosition(),
                atleta.getTreinoNotaGeral(),
                desempenhoFisico,
                tendencia,
                atleta.getPhoto()
            );
        }).collect(Collectors.toList());
    }

    /**
     * Calcula o desempenho físico em percentual baseado na nota física do treino
     */
    private int calcularDesempenhoFisico(Atleta atleta) {
        // Converte a nota física (0-10) para percentual (0-100)
        double notaFisica = atleta.getTreinoNotaFisica();
        return (int) ((notaFisica / 10.0) * 100);
    }

    /**
     * Calcula a tendência do atleta (up, down, stable)
     */
    private String calcularTendencia(Atleta atleta) {
        // TODO: Comparar com treinos anteriores do banco
        // Por enquanto, baseado na nota geral
        double nota = atleta.getTreinoNotaGeral();

        if (nota >= 9.0) {
            return "up";
        } else if (nota >= 7.0) {
            return "stable";
        } else {
            return "down";
        }
    }
}

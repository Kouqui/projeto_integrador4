//CRIAÇÃO PELO AUTOR: KAUÊ FARIAS LOURENÇO RA:24788788

package com.fut.fut360.controller;

import com.fut.fut360.Model.EstatisticaTemporada;
import com.fut.fut360.Repository.EstatisticaTemporadaRepository;
import com.fut.fut360.service.PerformanceService;
import com.fut.fut360.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ranking")
public class RankingController {

    @Autowired
    private EstatisticaTemporadaRepository estatisticaRepository;

    @Autowired
    private PerformanceService performanceService;

    @Autowired
    private SystemService systemService;

    // 1. Endpoint do Scout (Relatório de Jogadores)
    @GetMapping("/geral")
    public List<Map<String, Object>> getRelatorioScout() {
        List<EstatisticaTemporada> todasStats = estatisticaRepository.findAll();

        return todasStats.stream()
                .map(stat -> {
                    double score = performanceService.calcularScore(stat);
                    String fase = performanceService.definirFase(score);
                    String veredito = performanceService.gerarVereditoScout(stat.getAtleta(), score);
                    String valor = performanceService.estimarValorDeMercado(stat.getAtleta(), stat);

                    Map<String, Object> resposta = new java.util.HashMap<>();
                    resposta.put("nome", stat.getAtleta() != null ? stat.getAtleta().getName() : "Desconhecido");
                    resposta.put("posicao", stat.getAtleta() != null ? stat.getAtleta().getPosicao() : "-");
                    resposta.put("score", score);
                    resposta.put("fase", fase);
                    resposta.put("veredito", veredito);
                    resposta.put("valor", valor);

                    return resposta;
                })
                .sorted((a, b) -> Double.compare((Double) b.get("score"), (Double) a.get("score")))
                .limit(5)
                .collect(Collectors.toList());
    }

    // 2. Endpoint de Monitoramento (Hardware)
    @GetMapping("/sistema")
    public Map<String, Object> getDadosSistema() {
        Map<String, Object> dados = new java.util.HashMap<>();
        dados.put("acessos", systemService.getPaginasMaisAcessadas());
        dados.put("saude", systemService.getServerHealth());
        return dados;
    }

    // 3. Ações de Controle (Botões)
    @PostMapping("/sistema/limpar-logs")
    public void limparLogs() { systemService.limparLogs(); }

    @PostMapping("/sistema/resetar-views")
    public void resetarViews() { systemService.resetarContadores(); }

    @PostMapping("/sistema/otimizar-ram")
    public void otimizarRam() { systemService.executarGC(); }
}
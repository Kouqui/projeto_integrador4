package com.fut.fut360.service;

import java.lang.management.ManagementFactory;
import com.sun.management.OperatingSystemMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SystemService {

    private final Instant horaInicio = Instant.now();

    // Contadores REAIS
    private Map<String, Integer> estatisticasUso = new ConcurrentHashMap<>();

    public SystemService() {
        resetarContadores(); // Inicializa zerado
    }

    public void registrarUso(String tipoAcao) {
        estatisticasUso.merge(tipoAcao, 1, Integer::sum);
    }

    public Map<String, Integer> getEstatisticasReais() {
        // 1. Cria uma cópia dos dados reais de uso (Login, Scout, Agenda...)
        Map<String, Integer> estatisticasFinais = new HashMap<>(estatisticasUso);

        // 2. INJETA AS COLUNAS FIXAS (PLACEHOLDERS) COM VALOR 0
        // Como adicionamos aqui no final, garantimos que sempre serão 0.
        estatisticasFinais.put("Relatorios", 0);
        estatisticasFinais.put("Financeiro", 0);
        estatisticasFinais.put("Calendario", 0);

        return estatisticasFinais;
    }

    // --- NOVOS MÉTODOS DE LIMPEZA ---

    public void resetarContadores() {
        estatisticasUso.clear();
        estatisticasUso.put("Login", 0);
        estatisticasUso.put("Dashboard", 0);
        estatisticasUso.put("Scout", 0);
        estatisticasUso.put("Cadastro", 0);
        estatisticasUso.put("Agenda", 0);
        estatisticasUso.put("Cadastro Evento", 0);
        System.out.println("[SYSTEM] Contadores de acesso zerados pelo Admin.");
    }

    public void liberarMemoria() {
        long antes = Runtime.getRuntime().freeMemory();
        System.gc(); // Chama o Garbage Collector (Lixeiro do Java)
        long depois = Runtime.getRuntime().freeMemory();
        System.out.println("[SYSTEM] Limpeza de RAM executada. Liberado: " + ((depois - antes)/1024) + " KB");
    }

    // --------------------------------

    public Map<String, Object> getServerHealth() {
        Map<String, Object> health = new HashMap<>();
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        double cpuLoad = osBean.getCpuLoad() * 100;
        if (Double.isNaN(cpuLoad) || cpuLoad < 0) cpuLoad = 0.0;

        long freeMemory = Runtime.getRuntime().freeMemory();
        long totalMemory = Runtime.getRuntime().totalMemory();
        long usedMB = (totalMemory - freeMemory) / (1024 * 1024);

        Duration uptime = Duration.between(horaInicio, Instant.now());
        String tempoLigado = String.format("%02d:%02d:%02d", uptime.toHours(), uptime.toMinutesPart(), uptime.toSecondsPart());

        health.put("status", "ONLINE ");
        health.put("uptime", tempoLigado);
        health.put("cpuRaw", cpuLoad);
        health.put("ramUsed", usedMB);

        return health;
    }
}
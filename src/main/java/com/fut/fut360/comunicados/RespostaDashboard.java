package com.fut.fut360.comunicados;

import java.util.Map;

public class RespostaDashboard extends Comunicado {
    private Map<String, Object> hardwareStats;
    private Map<String, Integer> acessosPaginas; // Para o gráfico

    public RespostaDashboard(Map<String, Object> hardware, Map<String, Integer> acessos) {
        this.hardwareStats = hardware;
        this.acessosPaginas = acessos;
    }

    public Map<String, Object> getHardwareStats() { return hardwareStats; }
    public Map<String, Integer> getAcessosPaginas() { return acessosPaginas; }
}
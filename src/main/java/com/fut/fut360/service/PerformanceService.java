package com.fut.fut360.service;

import com.fut.fut360.Model.Atleta;
import com.fut.fut360.Model.EstatisticaTemporada;

public class PerformanceService {

    public double calcularScore(EstatisticaTemporada stats) {
        if (stats == null) return 0.0;
        double goals = (stats.getGoals() != null) ? stats.getGoals() : 0;
        double assists = (stats.getAssists() != null) ? stats.getAssists() : 0;
        double games = (stats.getGames() != null) ? stats.getGames() : 0;

        // Simples lógica para teste
        return (goals * 15.0) + (assists * 10.0) + (games * 2.0);
    }

    public String definirFase(double score) {
        if (score > 120) return "AUGE TÉCNICO";
        if (score > 60) return "REGULAR";
        return "MÁ FASE";
    }

    // --- MÉTODOS ADICIONADOS PARA CORRIGIR O ERRO ---

    public String gerarVereditoScout(Atleta atleta, double score) {
        if (score > 80) return "Recomendado para contratação";
        return "Observar evolução";
    }

    public String estimarValorDeMercado(Atleta atleta, EstatisticaTemporada stats) {
        return "R$ 1.500.000,00"; // Valor fixo para simulação
    }
}
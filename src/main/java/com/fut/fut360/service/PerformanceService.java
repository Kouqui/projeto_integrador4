package com.fut.fut360.service;

import com.fut.fut360.Model.EstatisticaTemporada;
import com.fut.fut360.Model.Atleta;
import org.springframework.stereotype.Service;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class PerformanceService {

    // 1. Calcula a Nota (Score 0 a 100)
    public double calcularScore(EstatisticaTemporada stats) {
        if (stats == null) return 0.0;

        double goals = (stats.getGoals() != null) ? stats.getGoals() : 0;
        double assists = (stats.getAssists() != null) ? stats.getAssists() : 0;
        double games = (stats.getGames() != null) ? stats.getGames() : 0;
        double yellow = (stats.getYellow() != null) ? stats.getYellow() : 0;
        double red = (stats.getRed() != null) ? stats.getRed() : 0;

        // Fórmula de Scout
        double score = (goals * 15.0) + (assists * 10.0) + (games * 2.0) - (yellow * 5.0) - (red * 20.0);
        return Math.max(score, 0);
    }

    // 2. Define o "Momentum" (Fase do Jogador)
    public String definirFase(double score) {
        if (score > 120) return " AUGE TÉCNICO";
        if (score > 60)  return " REGULAR";
        if (score > 20)  return " OSCILANDO";
        return " MÁ FASE";
    }

    // 3. Veredito Técnico
    public String gerarVereditoScout(Atleta atleta, double score) {
        if (score > 120) return "Titular absoluto. Prioridade máxima de renovação.";
        if (score > 80) return "Bom compor elenco. Útil para rotação.";
        if (score > 40) return "Precisa evoluir fisicamente para ganhar minutos.";
        return "Desempenho insuficiente. Avaliar possível empréstimo.";
    }

    // 4. Valuation (Valor de Mercado) - CORRIGIDO AQUI
    public String estimarValorDeMercado(Atleta atleta, EstatisticaTemporada stats) {
        if (atleta == null || stats == null) return "R$ 0,00";

        double valorBase = 500_000.00;
        int idade = atleta.getAge();

        // Fator Idade
        if (idade < 20) valorBase *= 3.0;
        else if (idade < 26) valorBase *= 1.5;
        else if (idade > 30) valorBase *= 0.6;

        // --- CORREÇÃO DO ERRO ---
        double goals = (stats.getGoals() != null) ? stats.getGoals() : 0;
        valorBase += (goals * 100_000.00); // Agora está usando "goals" igual a linha de cima

        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(valorBase);
    }
}
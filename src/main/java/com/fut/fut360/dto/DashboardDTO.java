package com.fut.fut360.dto;

import java.math.BigDecimal;
import java.util.List;

public class DashboardDTO {

    private FinanceiroResumo financeiroResumo;
    private List<EventoResumo> proximosEventos;
    private List<AtletaDesempenho> desempenhoAtletas;

    public DashboardDTO() {}

    public DashboardDTO(FinanceiroResumo financeiroResumo, List<EventoResumo> proximosEventos, List<AtletaDesempenho> desempenhoAtletas) {
        this.financeiroResumo = financeiroResumo;
        this.proximosEventos = proximosEventos;
        this.desempenhoAtletas = desempenhoAtletas;
    }

    // Getters e Setters
    public FinanceiroResumo getFinanceiroResumo() {
        return financeiroResumo;
    }

    public void setFinanceiroResumo(FinanceiroResumo financeiroResumo) {
        this.financeiroResumo = financeiroResumo;
    }

    public List<EventoResumo> getProximosEventos() {
        return proximosEventos;
    }

    public void setProximosEventos(List<EventoResumo> proximosEventos) {
        this.proximosEventos = proximosEventos;
    }

    public List<AtletaDesempenho> getDesempenhoAtletas() {
        return desempenhoAtletas;
    }

    public void setDesempenhoAtletas(List<AtletaDesempenho> desempenhoAtletas) {
        this.desempenhoAtletas = desempenhoAtletas;
    }

    // Classes internas para estruturar os dados

    public static class FinanceiroResumo {
        private BigDecimal saldoCaixa;
        private String percentualComparacao; // Ex: "+5.2%"
        private BigDecimal folhaSalarial;
        private String infoVencimento; // Ex: "Vencimento em 5 dias"

        public FinanceiroResumo() {}

        public FinanceiroResumo(BigDecimal saldoCaixa, String percentualComparacao, BigDecimal folhaSalarial, String infoVencimento) {
            this.saldoCaixa = saldoCaixa;
            this.percentualComparacao = percentualComparacao;
            this.folhaSalarial = folhaSalarial;
            this.infoVencimento = infoVencimento;
        }

        public BigDecimal getSaldoCaixa() {
            return saldoCaixa;
        }

        public void setSaldoCaixa(BigDecimal saldoCaixa) {
            this.saldoCaixa = saldoCaixa;
        }

        public String getPercentualComparacao() {
            return percentualComparacao;
        }

        public void setPercentualComparacao(String percentualComparacao) {
            this.percentualComparacao = percentualComparacao;
        }

        public BigDecimal getFolhaSalarial() {
            return folhaSalarial;
        }

        public void setFolhaSalarial(BigDecimal folhaSalarial) {
            this.folhaSalarial = folhaSalarial;
        }

        public String getInfoVencimento() {
            return infoVencimento;
        }

        public void setInfoVencimento(String infoVencimento) {
            this.infoVencimento = infoVencimento;
        }
    }

    public static class EventoResumo {
        private String dia;
        private String mes;
        private String titulo;
        private String descricao;
        private String categoria; // "professional", "sub-17", "general"

        public EventoResumo() {}

        public EventoResumo(String dia, String mes, String titulo, String descricao, String categoria) {
            this.dia = dia;
            this.mes = mes;
            this.titulo = titulo;
            this.descricao = descricao;
            this.categoria = categoria;
        }

        public String getDia() {
            return dia;
        }

        public void setDia(String dia) {
            this.dia = dia;
        }

        public String getMes() {
            return mes;
        }

        public void setMes(String mes) {
            this.mes = mes;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }
    }

    public static class AtletaDesempenho {
        private String nome;
        private String categoria;
        private String posicao;
        private double notaTreino;
        private int desempenhoFisicoPercentual;
        private String tendencia; // "up", "down", "stable"
        private String photoUrl;

        public AtletaDesempenho() {}

        public AtletaDesempenho(String nome, String categoria, String posicao, double notaTreino, int desempenhoFisicoPercentual, String tendencia, String photoUrl) {
            this.nome = nome;
            this.categoria = categoria;
            this.posicao = posicao;
            this.notaTreino = notaTreino;
            this.desempenhoFisicoPercentual = desempenhoFisicoPercentual;
            this.tendencia = tendencia;
            this.photoUrl = photoUrl;
        }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public String getPosicao() {
            return posicao;
        }

        public void setPosicao(String posicao) {
            this.posicao = posicao;
        }

        public double getNotaTreino() {
            return notaTreino;
        }

        public void setNotaTreino(double notaTreino) {
            this.notaTreino = notaTreino;
        }

        public int getDesempenhoFisicoPercentual() {
            return desempenhoFisicoPercentual;
        }

        public void setDesempenhoFisicoPercentual(int desempenhoFisicoPercentual) {
            this.desempenhoFisicoPercentual = desempenhoFisicoPercentual;
        }

        public String getTendencia() {
            return tendencia;
        }

        public void setTendencia(String tendencia) {
            this.tendencia = tendencia;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        // Método auxiliar para determinar a classe CSS da nota
        public String getNotaClass() {
            if (notaTreino >= 9.5) return "rating-perfect";
            if (notaTreino >= 9.0) return "rating-great";
            if (notaTreino >= 8.0) return "rating-good";
            if (notaTreino >= 6.0) return "rating-medium";
            return "rating-low";
        }
    }
}

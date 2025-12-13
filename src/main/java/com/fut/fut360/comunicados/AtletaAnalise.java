package com.fut.fut360.comunicados;

import com.fut.fut360.Model.Atleta;

public class AtletaAnalise extends Comunicado {
    private Atleta atleta;
    private String fase;
    private String veredito;
    private String valorMercadoEstimado;

    public AtletaAnalise(Atleta atleta, String fase, String veredito, String valor) {
        this.atleta = atleta;
        this.fase = fase;
        this.veredito = veredito;
        this.valorMercadoEstimado = valor;
    }

    public Atleta getAtleta() { return atleta; }
    public String getFase() { return fase; }
    public String getVeredito() { return veredito; }
    public String getValorMercadoEstimado() { return valorMercadoEstimado; }
}
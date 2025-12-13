package com.fut.fut360.comunicados;

import com.fut.fut360.Model.Atleta;

public class PedidoCadastrarAtleta extends Comunicado {
    private Atleta atleta;

    public PedidoCadastrarAtleta(Atleta atleta) {
        this.atleta = atleta;
    }

    public Atleta getAtleta() {
        return atleta;
    }
}
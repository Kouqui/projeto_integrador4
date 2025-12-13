package com.fut.fut360.comunicados;
import com.fut.fut360.Model.Evento;

public class PedidoCadastrarEvento extends Comunicado {
    private Evento evento;
    public PedidoCadastrarEvento(Evento evento) { this.evento = evento; }
    public Evento getEvento() { return evento; }
}
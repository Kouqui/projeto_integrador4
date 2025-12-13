package com.fut.fut360.Model;

import java.io.Serializable;

public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;

    private String titulo;
    private String data;       // Vamos usar String (formato YYYY-MM-DD) para facilitar o transporte
    private String horaInicio; // Formato HH:MM
    private String horaFim;    // Formato HH:MM
    private String tipo;       // JOGO, TREINO, REUNIAO
    private String categoria;
    private String local;
    private String adversario;
    private String descricao;

    public Evento() {}

    public Evento(String titulo, String data, String horaInicio, String horaFim, String tipo,
                  String categoria, String local, String adversario, String descricao) {
        this.titulo = titulo;
        this.data = data;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.tipo = tipo;
        this.categoria = categoria;
        this.local = local;
        this.adversario = adversario;
        this.descricao = descricao;
    }

    // Getters
    public String getTitulo() { return titulo; }
    public String getData() { return data; }
    public String getHoraInicio() { return horaInicio; }
    public String getHoraFim() { return horaFim; }
    public String getTipo() { return tipo; }
    public String getCategoria() { return categoria; }
    public String getLocal() { return local; }
    public String getAdversario() { return adversario; }
    public String getDescricao() { return descricao; }

    @Override
    public String toString() { return titulo + " (" + data + ")"; }
}
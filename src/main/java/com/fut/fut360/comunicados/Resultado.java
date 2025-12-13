package com.fut.fut360.comunicados;

public class Resultado extends Comunicado {
    private double valorResultante;
    private String mensagem; // Adicionei msg para feedbacks de texto (ex: "Login OK")

    // Construtor original do professor (apenas double)
    public Resultado(double valorResultante) {
        this.valorResultante = valorResultante;
        this.mensagem = "";
    }

    // Construtor extra para facilitar mensagens
    public Resultado(double valorResultante, String mensagem) {
        this.valorResultante = valorResultante;
        this.mensagem = mensagem;
    }

    public double getValorResultante() { return this.valorResultante; }
    public String getMensagem() { return this.mensagem; }
}
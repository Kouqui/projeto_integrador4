package com.fut.fut360.Model;

import java.io.Serializable;

public class Atleta implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String posicao;
    private int idade;
    private double valorMercado;

    // Novos campos
    private String nacionalidade;
    private int alturaCm;
    private double pesoKg;
    private String peDominante;
    private String categoria;

    // 1. Construtor Vazio (Obrigatório)
    public Atleta() {}

    // 2. Construtor SIMPLES (Para compatibilidade com o RepositorioMemoria antigo)
    // Esse é o que estava faltando e causou o erro!
    public Atleta(String nome, String posicao, int idade, double valorMercado) {
        this.nome = nome;
        this.posicao = posicao;
        this.idade = idade;
        this.valorMercado = valorMercado;
        // Preenche o resto com padrão para não dar erro
        this.nacionalidade = "Brasil";
        this.alturaCm = 0;
        this.pesoKg = 0.0;
        this.peDominante = "Destro";
        this.categoria = "Profissional";
    }

    // 3. Construtor COMPLETO (Para o Banco de Dados e Cadastro Novo)
    public Atleta(String nome, String posicao, int idade, double valorMercado,
                  String nacionalidade, int alturaCm, double pesoKg, String peDominante, String categoria) {
        this.nome = nome;
        this.posicao = posicao;
        this.idade = idade;
        this.valorMercado = valorMercado;
        this.nacionalidade = nacionalidade;
        this.alturaCm = alturaCm;
        this.pesoKg = pesoKg;
        this.peDominante = peDominante;
        this.categoria = categoria;
    }

    // --- Getters e Setters ---
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPosicao() { return posicao; }
    public void setPosicao(String posicao) { this.posicao = posicao; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public double getValorMercado() { return valorMercado; }
    public void setValorMercado(double valorMercado) { this.valorMercado = valorMercado; }

    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }

    public int getAlturaCm() { return alturaCm; }
    public void setAlturaCm(int alturaCm) { this.alturaCm = alturaCm; }

    public double getPesoKg() { return pesoKg; }
    public void setPesoKg(double pesoKg) { this.pesoKg = pesoKg; }

    public String getPeDominante() { return peDominante; }
    public void setPeDominante(String peDominante) { this.peDominante = peDominante; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return nome + " - " + posicao;
    }
}
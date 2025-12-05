//Criação pelo autor: Kauã Kouqui
package com.fut.fut360.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "estatisticas_temporada")
public class EstatisticaTemporada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String temporada;

    @Column(name = "jogos")
    private Integer games;
    @Column(name = "minutos")
    private Integer minutes;
    @Column(name = "gols")
    private Integer goals;
    private Integer assists;
    @Column(name = "cartoes_amarelos")
    private Integer yellow;
    @Column(name = "cartoes_vermelhos")
    private Integer red;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atleta_id", nullable = false)
    private Atleta atleta;

    public EstatisticaTemporada() {}

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTemporada() { return temporada; }
    public void setTemporada(String temporada) { this.temporada = temporada; }
    public Integer getGames() { return games; }
    public void setGames(Integer games) { this.games = games; }
    public Integer getMinutes() { return minutes; }
    public void setMinutes(Integer minutes) { this.minutes = minutes; }
    public Integer getGoals() { return goals; }
    public void setGoals(Integer goals) { this.goals = goals; }
    public Integer getAssists() { return assists; }
    public void setAssists(Integer assists) { this.assists = assists; }
    public Integer getYellow() { return yellow; }
    public void setYellow(Integer yellow) { this.yellow = yellow; }
    public Integer getRed() { return red; }
    public void setRed(Integer red) { this.red = red; }
    public Atleta getAtleta() { return atleta; }
    public void setAtleta(Atleta atleta) { this.atleta = atleta; }
}
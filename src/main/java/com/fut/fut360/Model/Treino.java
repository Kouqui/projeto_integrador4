package com.fut.fut360.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Entity
@Table(name = "treinos")
public class Treino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_treino", nullable = false)
    private LocalDate dataTreino;

    @Column(name = "nota_tatica")
    private BigDecimal treinoNotaTatica;
    @Column(name = "nota_fisica")
    private BigDecimal treinoNotaFisica;
    @Column(name = "passes_certos")
    private Integer treinoPassesCertos;
    @Column(name = "passes_total")
    private Integer treinoPassesTotal;
    @Column(name = "chutes_certos")
    private Integer treinoChutesCertos;
    @Column(name = "chutes_total")
    private Integer treinoChutesTotal;
    @Column(name = "dribles_certos")
    private Integer treinoDriblesCertos;
    @Column(name = "dribles_total")
    private Integer treinoDriblesTotal;
    @Column(name = "desarmes")
    private Integer treinoDesarmes;
    @Column(name = "interceptacoes")
    private Integer treinoInterceptacoes;
    @Column(name = "km_percorridos")
    private BigDecimal treinoKmPercorridos;
    @Column(name = "velocidade_max")
    private BigDecimal treinoVelocidadeMax;
    @Column(name = "sprints")
    private Integer treinoSprints;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atleta_id", nullable = false)
    private Atleta atleta;

    public Treino() {}

    // --- MÉTODOS DE CÁLCULO (À PROVA DE NULLS) ---
    private int safeInt(Integer value) { return (value != null) ? value : 0; }
    private BigDecimal safeDecimal(BigDecimal value) { return (value != null) ? value : BigDecimal.ZERO; }

    @Transient
    public BigDecimal getTreinoNotaTecnica() {
        int passesCertos = safeInt(this.treinoPassesCertos);
        int passesTotal = safeInt(this.treinoPassesTotal);
        int chutesCertos = safeInt(this.treinoChutesCertos);
        int chutesTotal = safeInt(this.treinoChutesTotal);
        int driblesCertos = safeInt(this.treinoDriblesCertos);
        int driblesTotal = safeInt(this.treinoDriblesTotal);

        double passePct = (passesTotal > 0) ? ((double) passesCertos / passesTotal) : 0;
        double chutePct = (chutesTotal > 0) ? ((double) chutesCertos / chutesTotal) : 0;
        double driblePct = (driblesTotal > 0) ? ((double) driblesCertos / driblesTotal) : 0;

        int count = 0;
        if (passesTotal > 0) count++;
        if (chutesTotal > 0) count++;
        if (driblesTotal > 0) count++;
        if (count == 0) return BigDecimal.ZERO;

        double mediaTecnica = (passePct + chutePct + driblePct) / count * 10;
        return BigDecimal.valueOf(mediaTecnica).setScale(1, RoundingMode.HALF_UP);
    }

    @Transient
    public BigDecimal getTreinoNotaGeral() {
        BigDecimal tatica = safeDecimal(this.treinoNotaTatica);
        BigDecimal tecnica = getTreinoNotaTecnica();
        BigDecimal fisica = safeDecimal(this.treinoNotaFisica);

        BigDecimal nota = (tatica.multiply(new BigDecimal("0.3")))
                .add(tecnica.multiply(new BigDecimal("0.4")))
                .add(fisica.multiply(new BigDecimal("0.3")));
        return nota.setScale(1, RoundingMode.HALF_UP);
    }

    // --- GETTERS E SETTERS COMPLETOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDataTreino() { return dataTreino; }
    public void setDataTreino(LocalDate dataTreino) { this.dataTreino = dataTreino; }
    public BigDecimal getTreinoNotaTatica() { return treinoNotaTatica; }
    public void setTreinoNotaTatica(BigDecimal treinoNotaTatica) { this.treinoNotaTatica = treinoNotaTatica; }
    public BigDecimal getTreinoNotaFisica() { return treinoNotaFisica; }
    public void setTreinoNotaFisica(BigDecimal treinoNotaFisica) { this.treinoNotaFisica = treinoNotaFisica; }
    public Integer getTreinoPassesCertos() { return treinoPassesCertos; }
    public void setTreinoPassesCertos(Integer treinoPassesCertos) { this.treinoPassesCertos = treinoPassesCertos; }
    public Integer getTreinoPassesTotal() { return treinoPassesTotal; }
    public void setTreinoPassesTotal(Integer treinoPassesTotal) { this.treinoPassesTotal = treinoPassesTotal; }
    public Integer getTreinoChutesCertos() { return treinoChutesCertos; }
    public void setTreinoChutesCertos(Integer treinoChutesCertos) { this.treinoChutesCertos = treinoChutesCertos; }
    public Integer getTreinoChutesTotal() { return treinoChutesTotal; }
    public void setTreinoChutesTotal(Integer treinoChutesTotal) { this.treinoChutesTotal = treinoChutesTotal; }
    public Integer getTreinoDriblesCertos() { return treinoDriblesCertos; }
    public void setTreinoDriblesCertos(Integer treinoDriblesCertos) { this.treinoDriblesCertos = treinoDriblesCertos; }
    public Integer getTreinoDriblesTotal() { return treinoDriblesTotal; }
    public void setTreinoDriblesTotal(Integer treinoDriblesTotal) { this.treinoDriblesTotal = treinoDriblesTotal; }
    public Integer getTreinoDesarmes() { return treinoDesarmes; }
    public void setTreinoDesarmes(Integer treinoDesarmes) { this.treinoDesarmes = treinoDesarmes; }
    public Integer getTreinoInterceptacoes() { return treinoInterceptacoes; }
    public void setTreinoInterceptacoes(Integer treinoInterceptacoes) { this.treinoInterceptacoes = treinoInterceptacoes; }
    public BigDecimal getTreinoKmPercorridos() { return treinoKmPercorridos; }
    public void setTreinoKmPercorridos(BigDecimal treinoKmPercorridos) { this.treinoKmPercorridos = treinoKmPercorridos; }
    public BigDecimal getTreinoVelocidadeMax() { return treinoVelocidadeMax; }
    public void setTreinoVelocidadeMax(BigDecimal treinoVelocidadeMax) { this.treinoVelocidadeMax = treinoVelocidadeMax; }
    public Integer getTreinoSprints() { return treinoSprints; }
    public void setTreinoSprints(Integer treinoSprints) { this.treinoSprints = treinoSprints; }
    public Atleta getAtleta() { return atleta; }
    public void setAtleta(Atleta atleta) { this.atleta = atleta; }
}
//CRIAÇÃO PELO AUTOR: KAUÊ FARIAS LOURENÇO RA:24788788

package com.fut.fut360.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "eventos_estatisticas")
public class EventoEstatistica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "posse_bola_time1", length = 10)
    private String posseBolaTime1;

    @Column(name = "posse_bola_time2", length = 10)
    private String posseBolaTime2;

    @Column(name = "chutes_a_gol_time1")
    private Integer chutesAGolTime1;

    @Column(name = "chutes_a_gol_time2")
    private Integer chutesAGolTime2;

    @Column(name = "escanteios_time1")
    private Integer escanteiosTime1;

    @Column(name = "escanteios_time2")
    private Integer escanteiosTime2;

    @Column(name = "faltas_time1")
    private Integer faltasTime1;

    @Column(name = "faltas_time2")
    private Integer faltasTime2;

    @Column(name = "cartoes_amarelos_time1")
    private Integer cartoesAmarelosTime1;

    @Column(name = "cartoes_amarelos_time2")
    private Integer cartoesAmarelosTime2;

    @Column(name = "cartoes_vermelhos_time1")
    private Integer cartoesVermelhosTime1;

    @Column(name = "cartoes_vermelhos_time2")
    private Integer cartoesVermelhosTime2;

    @Column(name = "gols_time1")
    private Integer golsTime1;

    @Column(name = "gols_time2")
    private Integer golsTime2;

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public String getPosseBolaTime1() { return posseBolaTime1; }
    public void setPosseBolaTime1(String posseBolaTime1) { this.posseBolaTime1 = posseBolaTime1; }

    public String getPosseBolaTime2() { return posseBolaTime2; }
    public void setPosseBolaTime2(String posseBolaTime2) { this.posseBolaTime2 = posseBolaTime2; }

    public Integer getChutesAGolTime1() { return chutesAGolTime1; }
    public void setChutesAGolTime1(Integer chutesAGolTime1) { this.chutesAGolTime1 = chutesAGolTime1; }

    public Integer getChutesAGolTime2() { return chutesAGolTime2; }
    public void setChutesAGolTime2(Integer chutesAGolTime2) { this.chutesAGolTime2 = chutesAGolTime2; }

    public Integer getEscanteiosTime1() { return escanteiosTime1; }
    public void setEscanteiosTime1(Integer escanteiosTime1) { this.escanteiosTime1 = escanteiosTime1; }

    public Integer getEscanteiosTime2() { return escanteiosTime2; }
    public void setEscanteiosTime2(Integer escanteiosTime2) { this.escanteiosTime2 = escanteiosTime2; }

    public Integer getFaltasTime1() { return faltasTime1; }
    public void setFaltasTime1(Integer faltasTime1) { this.faltasTime1 = faltasTime1; }

    public Integer getFaltasTime2() { return faltasTime2; }
    public void setFaltasTime2(Integer faltasTime2) { this.faltasTime2 = faltasTime2; }

    public Integer getCartoesAmarelosTime1() { return cartoesAmarelosTime1; }
    public void setCartoesAmarelosTime1(Integer cartoesAmarelosTime1) { this.cartoesAmarelosTime1 = cartoesAmarelosTime1; }

    public Integer getCartoesAmarelosTime2() { return cartoesAmarelosTime2; }
    public void setCartoesAmarelosTime2(Integer cartoesAmarelosTime2) { this.cartoesAmarelosTime2 = cartoesAmarelosTime2; }

    public Integer getCartoesVermelhosTime1() { return cartoesVermelhosTime1; }
    public void setCartoesVermelhosTime1(Integer cartoesVermelhosTime1) { this.cartoesVermelhosTime1 = cartoesVermelhosTime1; }

    public Integer getCartoesVermelhosTime2() { return cartoesVermelhosTime2; }
    public void setCartoesVermelhosTime2(Integer cartoesVermelhosTime2) { this.cartoesVermelhosTime2 = cartoesVermelhosTime2; }

    public Integer getGolsTime1() { return golsTime1; }
    public void setGolsTime1(Integer golsTime1) { this.golsTime1 = golsTime1; }

    public Integer getGolsTime2() { return golsTime2; }
    public void setGolsTime2(Integer golsTime2) { this.golsTime2 = golsTime2; }
}

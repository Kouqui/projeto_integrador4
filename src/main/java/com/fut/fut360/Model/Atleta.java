package com.fut.fut360.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList; // Importante
import java.util.List;

@Entity
@Table(name = "atletas")
public class Atleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_completo")
    private String fullname;

    @Column(name = "nome_curto")
    private String name;

    @Column(name = "data_nascimento")
    private LocalDate birthdate;

    private String nacionalidade;

    @Column(name = "altura_cm")
    private Integer alturaCm;

    @Column(name = "peso_kg")
    private BigDecimal pesoKg;

    @Column(name = "pe_dominante")
    private String foot;

    private String posicao;
    private String categoria;

    @Column(name = "foto_url")
    private String photo;

    // --- RELACIONAMENTOS ---
    // Inicializamos com 'new ArrayList<>()' para evitar NULL, mas os métodos helper garantem segurança extra

    @OneToMany(mappedBy = "atleta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id DESC")
    private List<Contrato> contratos = new ArrayList<>();

    @OneToMany(mappedBy = "atleta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id DESC")
    private List<Treino> treinos = new ArrayList<>();

    @OneToMany(mappedBy = "atleta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id DESC")
    private List<EstatisticaTemporada> estatisticas = new ArrayList<>();

    @OneToMany(mappedBy = "atleta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @OrderBy("id DESC")
    private List<RegistroCarreira> registros = new ArrayList<>();

    public Atleta() {}

    // --- MÉTODOS HELPER BLINDADOS (CORREÇÃO DO ERRO) ---

    public void addContrato(Contrato contrato) {
        if (this.contratos == null) {
            this.contratos = new ArrayList<>();
        }
        this.contratos.add(contrato);
        contrato.setAtleta(this);
    }

    public void addTreino(Treino treino) {
        if (this.treinos == null) {
            this.treinos = new ArrayList<>();
        }
        this.treinos.add(treino);
        treino.setAtleta(this);
    }

    public void addEstatistica(EstatisticaTemporada est) {
        if (this.estatisticas == null) {
            this.estatisticas = new ArrayList<>();
        }
        this.estatisticas.add(est);
        est.setAtleta(this);
    }

    public void addRegistro(RegistroCarreira reg) {
        if (this.registros == null) {
            this.registros = new ArrayList<>();
        }
        this.registros.add(reg);
        reg.setAtleta(this);
    }

    // --- LÓGICA DE NEGÓCIO ---
    public int getAge() {
        if (this.birthdate == null) return 0;
        return Period.between(this.birthdate, LocalDate.now()).getYears();
    }

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public LocalDate getBirthdate() { return birthdate; }
    public void setBirthdate(LocalDate birthdate) { this.birthdate = birthdate; }
    public String getNacionalidade() { return nacionalidade; }
    public void setNacionalidade(String nacionalidade) { this.nacionalidade = nacionalidade; }
    public Integer getAlturaCm() { return alturaCm; }
    public void setAlturaCm(Integer alturaCm) { this.alturaCm = alturaCm; }
    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }
    public String getFoot() { return foot; }
    public void setFoot(String foot) { this.foot = foot; }
    public String getPosicao() { return posicao; }
    public void setPosicao(String posicao) { this.posicao = posicao; }
    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public List<Contrato> getContratos() { return contratos; }
    public void setContratos(List<Contrato> contratos) { this.contratos = contratos; }

    public List<Treino> getTreinos() { return treinos; }
    public void setTreinos(List<Treino> treinos) { this.treinos = treinos; }

    public List<EstatisticaTemporada> getEstatisticas() { return estatisticas; }
    public void setEstatisticas(List<EstatisticaTemporada> estatisticas) { this.estatisticas = estatisticas; }

    public List<RegistroCarreira> getRegistros() { return registros; }
    public void setRegistros(List<RegistroCarreira> registros) { this.registros = registros; }
}
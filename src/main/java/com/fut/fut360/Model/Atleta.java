package com.fut.fut360.model;

import jakarta.persistence.*;

@Entity
@Table(name = "atletas")
public class Atleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ficha Técnica
    private String name, position, category, photo, fullname, birthdate, nationality, height, weight, foot;
    private int age;

    // Desempenho (Temporada)
    private int games, minutes, goals, assists;

    @Column(name = "yellow_cards")
    private int yellow;

    @Column(name = "red_cards")
    private int red;

    // Carreira & Metas
    private String meta1, meta2, observation;

    // Financeiro
    private String salary;

    @Column(name = "contract_start")
    private String contractStart;

    @Column(name = "contract_end")
    private String contractEnd;

    @Column(name = "release_clause")
    private String releaseClause;

    // Análise do Último Treino
    @Column(name = "treino_nota_geral")
    private double treinoNotaGeral;

    @Column(name = "treino_nota_tatica")
    private double treinoNotaTatica;

    @Column(name = "treino_nota_tecnica")
    private double treinoNotaTecnica;

    @Column(name = "treino_nota_fisica")
    private double treinoNotaFisica;

    @Column(name = "treino_passes_certos")
    private int treinoPassesCertos;

    @Column(name = "treino_passes_total")
    private int treinoPassesTotal;

    @Column(name = "treino_chutes_certos")
    private int treinoChutesCertos;

    @Column(name = "treino_chutes_total")
    private int treinoChutesTotal;

    @Column(name = "treino_dribles_certos")
    private int treinoDriblesCertos;

    @Column(name = "treino_dribles_total")
    private int treinoDriblesTotal;

    @Column(name = "treino_desarmes")
    private int treinoDesarmes;

    @Column(name = "treino_interceptacoes")
    private int treinoInterceptacoes;

    @Column(name = "treino_km_percorridos")
    private double treinoKmPercorridos;

    @Column(name = "treino_velocidade_max")
    private double treinoVelocidadeMax;

    @Column(name = "treino_sprints")
    private int treinoSprints;

    // Construtor vazio (Boa prática)
    public Atleta() {}

    // Construtor atualizado com TODOS os campos
    public Atleta(String name, String position, String category, String photo, String fullname, String birthdate, int age, String nationality, String height, String weight, String foot,
                  int games, int minutes, int goals, int assists, int yellow, int red,
                  String meta1, String meta2, String observation,
                  String salary, String contractStart, String contractEnd, String releaseClause,
                  double treinoNotaGeral, double treinoNotaTatica, double treinoNotaTecnica, double treinoNotaFisica,
                  int treinoPassesCertos, int treinoPassesTotal, int treinoChutesCertos, int treinoChutesTotal,
                  int treinoDriblesCertos, int treinoDriblesTotal, int treinoDesarmes, int treinoInterceptacoes,
                  double treinoKmPercorridos, double treinoVelocidadeMax, int treinoSprints) {
        this.name = name;
        this.position = position;
        this.category = category;
        this.photo = photo;
        this.fullname = fullname;
        this.birthdate = birthdate;
        this.age = age;
        this.nationality = nationality;
        this.height = height;
        this.weight = weight;
        this.foot = foot;
        this.games = games;
        this.minutes = minutes;
        this.goals = goals;
        this.assists = assists;
        this.yellow = yellow;
        this.red = red;
        this.meta1 = meta1;
        this.meta2 = meta2;
        this.observation = observation;
        this.salary = salary;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
        this.releaseClause = releaseClause;
        this.treinoNotaGeral = treinoNotaGeral;
        this.treinoNotaTatica = treinoNotaTatica;
        this.treinoNotaTecnica = treinoNotaTecnica;
        this.treinoNotaFisica = treinoNotaFisica;
        this.treinoPassesCertos = treinoPassesCertos;
        this.treinoPassesTotal = treinoPassesTotal;
        this.treinoChutesCertos = treinoChutesCertos;
        this.treinoChutesTotal = treinoChutesTotal;
        this.treinoDriblesCertos = treinoDriblesCertos;
        this.treinoDriblesTotal = treinoDriblesTotal;
        this.treinoDesarmes = treinoDesarmes;
        this.treinoInterceptacoes = treinoInterceptacoes;
        this.treinoKmPercorridos = treinoKmPercorridos;
        this.treinoVelocidadeMax = treinoVelocidadeMax;
        this.treinoSprints = treinoSprints;
    }

    // --- GETTERS E SETTERS COMPLETOS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }
    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getHeight() { return height; }
    public void setHeight(String height) { this.height = height; }
    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }
    public String getFoot() { return foot; }
    public void setFoot(String foot) { this.foot = foot; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public int getGames() { return games; }
    public void setGames(int games) { this.games = games; }
    public int getMinutes() { return minutes; }
    public void setMinutes(int minutes) { this.minutes = minutes; }
    public int getGoals() { return goals; }
    public void setGoals(int goals) { this.goals = goals; }
    public int getAssists() { return assists; }
    public void setAssists(int assists) { this.assists = assists; }
    public int getYellow() { return yellow; }
    public void setYellow(int yellow) { this.yellow = yellow; }
    public int getRed() { return red; }
    public void setRed(int red) { this.red = red; }
    public String getMeta1() { return meta1; }
    public void setMeta1(String meta1) { this.meta1 = meta1; }
    public String getMeta2() { return meta2; }
    public void setMeta2(String meta2) { this.meta2 = meta2; }
    public String getObservation() { return observation; }
    public void setObservation(String observation) { this.observation = observation; }
    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
    public String getContractStart() { return contractStart; }
    public void setContractStart(String contractStart) { this.contractStart = contractStart; }
    public String getContractEnd() { return contractEnd; }
    public void setContractEnd(String contractEnd) { this.contractEnd = contractEnd; }
    public String getReleaseClause() { return releaseClause; }
    public void setReleaseClause(String releaseClause) { this.releaseClause = releaseClause; }
    public double getTreinoNotaGeral() { return treinoNotaGeral; }
    public void setTreinoNotaGeral(double treinoNotaGeral) { this.treinoNotaGeral = treinoNotaGeral; }
    public double getTreinoNotaTatica() { return treinoNotaTatica; }
    public void setTreinoNotaTatica(double treinoNotaTatica) { this.treinoNotaTatica = treinoNotaTatica; }
    public double getTreinoNotaTecnica() { return treinoNotaTecnica; }
    public void setTreinoNotaTecnica(double treinoNotaTecnica) { this.treinoNotaTecnica = treinoNotaTecnica; }
    public double getTreinoNotaFisica() { return treinoNotaFisica; }
    public void setTreinoNotaFisica(double treinoNotaFisica) { this.treinoNotaFisica = treinoNotaFisica; }
    public int getTreinoPassesCertos() { return treinoPassesCertos; }
    public void setTreinoPassesCertos(int treinoPassesCertos) { this.treinoPassesCertos = treinoPassesCertos; }
    public int getTreinoPassesTotal() { return treinoPassesTotal; }
    public void setTreinoPassesTotal(int treinoPassesTotal) { this.treinoPassesTotal = treinoPassesTotal; }
    public int getTreinoChutesCertos() { return treinoChutesCertos; }
    public void setTreinoChutesCertos(int treinoChutesCertos) { this.treinoChutesCertos = treinoChutesCertos; }
    public int getTreinoChutesTotal() { return treinoChutesTotal; }
    public void setTreinoChutesTotal(int treinoChutesTotal) { this.treinoChutesTotal = treinoChutesTotal; }
    public int getTreinoDriblesCertos() { return treinoDriblesCertos; }
    public void setTreinoDriblesCertos(int treinoDriblesCertos) { this.treinoDriblesCertos = treinoDriblesCertos; }
    public int getTreinoDriblesTotal() { return treinoDriblesTotal; }
    public void setTreinoDriblesTotal(int treinoDriblesTotal) { this.treinoDriblesTotal = treinoDriblesTotal; }
    public int getTreinoDesarmes() { return treinoDesarmes; }
    public void setTreinoDesarmes(int treinoDesarmes) { this.treinoDesarmes = treinoDesarmes; }
    public int getTreinoInterceptacoes() { return treinoInterceptacoes; }
    public void setTreinoInterceptacoes(int treinoInterceptacoes) { this.treinoInterceptacoes = treinoInterceptacoes; }
    public double getTreinoKmPercorridos() { return treinoKmPercorridos; }
    public void setTreinoKmPercorridos(double treinoKmPercorridos) { this.treinoKmPercorridos = treinoKmPercorridos; }
    public double getTreinoVelocidadeMax() { return treinoVelocidadeMax; }
    public void setTreinoVelocidadeMax(double treinoVelocidadeMax) { this.treinoVelocidadeMax = treinoVelocidadeMax; }
    public int getTreinoSprints() { return treinoSprints; }
    public void setTreinoSprints(int treinoSprints) { this.treinoSprints = treinoSprints; }
}
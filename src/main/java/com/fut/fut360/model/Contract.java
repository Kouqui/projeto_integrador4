//Criação pelo autor: Jefferson Andrey Dias Cardoso - Ra: 24017498

package com.fut.fut360.Model; // Ou Model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "contratos")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "salario")
    private BigDecimal salario;

    @Column(name = "clausula_rescisao")
    private BigDecimal clausula;

    @Column(name = "data_inicio")
    private LocalDate startDate;

    @Column(name = "data_fim")
    private LocalDate endDate;

    @Column(name = "ativo")
    private Boolean ativo = true;

    // --- MUDANÇA AQUI: Removemos o CascadeType.ALL ---
    // Agora só vinculamos, não criamos nem deletamos atletas por aqui.
    @ManyToOne
    @JoinColumn(name = "atleta_id", nullable = false)
    @JsonIgnoreProperties("contratos")
    private Atleta atleta;

    // DTOs (Dados da tela)
    @Transient private String name;     // O nome que o usuário digitou
    @Transient private Integer age;
    @Transient private String position;
    @Transient private String documentUrl;

    public Contract() {}

    // --- MUDANÇA AQUI: Removemos o @PrePersist que dava new Atleta() ---
    // A lógica de busca agora ficará no SERVICE, que é o lugar correto.

    @PostLoad
    private void preencherCamposTemporarios() {
        if (this.atleta != null) {
            this.name = this.atleta.getName();
            // A idade é calculada no Atleta, então apenas lemos
            this.age = this.atleta.getAge();
            this.position = this.atleta.getPosicao();
        }
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }
    public BigDecimal getClausula() { return clausula; }
    public void setClausula(BigDecimal clausula) { this.clausula = clausula; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public Boolean getAtivo() { return ativo; }
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }
    public Atleta getAtleta() { return atleta; }
    public void setAtleta(Atleta atleta) { this.atleta = atleta; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public String getDocumentUrl() { return documentUrl; }
    public void setDocumentUrl(String documentUrl) { this.documentUrl = documentUrl; }
}
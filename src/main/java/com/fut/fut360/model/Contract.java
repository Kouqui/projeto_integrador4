package com.fut.fut360. Model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "contratos2")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Coluna 'nome' no banco
    @Column(name = "nome", nullable = false)
    private String name;

    // Coluna 'idade' no banco
    @Column(name = "idade", nullable = false)
    private int age;

    // Coluna 'posicao' no banco
    @Column(name = "posicao", nullable = false)
    private String position;

    @Column(name = "salario") // <--- TEM QUE TER ESTA ANOTAÇÃO
    private BigDecimal salario;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_inicio", nullable = false)
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_termino", nullable = false) // No seu print estava 'data_termino'
    private LocalDate endDate;

    @Column(name = "documento_url")
    private String documentUrl;

    // --- CONSTRUTORES ---
    public Contract() {}

    public Contract(Long id, String name, int age, String position, LocalDate startDate, LocalDate endDate, String documentUrl) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.documentUrl = documentUrl;
    }

    // --- GETTERS E SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public String getDocumentUrl() { return documentUrl; }
    public void setDocumentUrl(String documentUrl) { this.documentUrl = documentUrl; }
}
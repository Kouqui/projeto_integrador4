package com.fut.fut360.Model;

import java.time.LocalDate;

// Modelo de dados para Contratos de Atletas
public class Contract {

    private Long id;
    private String name;         // Nome do atleta
    private int age;             // Idade
    private String position;     // Posição (Ex: Atacante, Goleiro)
    private LocalDate startDate; // Data de início do contrato
    private LocalDate endDate;   // Data de término do contrato
    private String documentUrl;  // Simulação de URL/Caminho para documento digitalizado

    public Contract(Long id, String name, int age, String position, LocalDate startDate, LocalDate endDate, String documentUrl) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.position = position;
        this.startDate = startDate;
        this.endDate = endDate;
        this.documentUrl = documentUrl;
    }

    // --- Getters e Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }
    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
    public String getDocumentUrl() { return documentUrl; }
    public void setDocumentUrl(String documentUrl) { this.documentUrl = documentUrl; }
}

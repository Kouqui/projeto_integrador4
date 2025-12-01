package com.fut.fut360.Model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

// Modelo de dados para transações financeiras (Receita/Despesa)
public class Transaction {

    private Long id;
    private String type;        // 'receita' ou 'despesa'
    private String description;
    private BigDecimal value;   // Valor monetário
    private LocalDate date;     // Data da transação
    private String category;
    private String responsible;

    // Construtor padrão (necessário)
    public Transaction(Long id, String type, String description, BigDecimal value, LocalDate date, String category, String responsible) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.value = value;
        this.date = date;
        this.category = category;
        this.responsible = responsible;
    }

    // --- Getters e Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

}

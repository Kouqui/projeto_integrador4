package com.fut.fut360.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime; // Import necessário para o timestamp
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "transacoes")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- MAPEAMENTO DAS COLUNAS ---

    @Column(name = "tipo", nullable = false)
    private String type;

    @Column(name = "descricao", nullable = false)
    private String description;

    @Column(name = "valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal value;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_transacao", nullable = false)
    private LocalDate date;

    @Column(name = "categoria")
    private String category;

    @Column(name = "responsavel")
    private String responsible;

    // --- CORREÇÃO DO ERRO DE TIMESTAMP ---
    // Inicializamos com .now() para que o banco receba o horário automaticamente
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    // --- CONSTRUTOR VAZIO (OBRIGATÓRIO) ---
    public Transaction() {
    }

    // --- CONSTRUTOR COM ARGUMENTOS ---
    // Note que NÃO pedimos o timestamp aqui, ele é gerado sozinho acima.
    public Transaction(Long id, String type, String description, BigDecimal value, LocalDate date, String category, String responsible) {
        this.id = id;
        this.type = type;
        this.description = description;
        this.value = value;
        this.date = date;
        this.category = category;
        this.responsible = responsible;
    }

    // --- GETTERS E SETTERS ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getResponsible() { return responsible; }
    public void setResponsible(String responsible) { this.responsible = responsible; }

    // Getters e Setters do Timestamp
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}
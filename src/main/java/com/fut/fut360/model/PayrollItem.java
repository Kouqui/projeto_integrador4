package com.fut.fut360.Model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "folha_salarial") // Nome exato da tabela no MySQL
public class PayrollItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome") // No Banco é 'nome', no Java é 'name'
    private String name;

    @Column(name = "funcao") // No Banco é 'funcao', no Java é 'role'
    private String role;

    @Column(name = "valor") // Garante o mapeamento do valor
    private BigDecimal value;

    @Column(name = "status")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd") // Aceita a data do HTML
    @Column(name = "data_pagamento") // No Banco é 'data_pagamento'
    private LocalDate paymentDate;

    // --- CONSTRUTORES ---
    public PayrollItem() {}

    public PayrollItem(Long id, String name, String role, BigDecimal value, String status, LocalDate paymentDate) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.value = value;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    // --- GETTERS E SETTERS (Obrigatórios para o Banco funcionar) ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
}
package com.fut.fut360.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "payroll_items")
public class PayrollItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;        // Nome do funcionário ou atleta
    private String role;        // Cargo / Função
    private BigDecimal value;   // Valor do pagamento (salário)
    private String status;      // 'pago' ou 'pendente'

    @Column(name = "payment_date")
    private LocalDate paymentDate; // Data de pagamento

    public PayrollItem() {}

    public PayrollItem(Long id, String name, String role, BigDecimal value, String status, LocalDate paymentDate) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.value = value;
        this.status = status;
        this.paymentDate = paymentDate;
    }

    // --- Getters e Setters ---
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

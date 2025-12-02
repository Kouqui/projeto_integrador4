package com.fut.fut360.service;

import com.fut.fut360.Model.Transaction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service // Define que esta é uma classe de Serviço do Spring
public class TransactionService {

    // Simula a base de dados (em memória)
    private final List<Transaction> storage = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    // Construtor com dados de exemplo
    public TransactionService() {

        save(new Transaction(null, "receita", "Patrocínio Máster", new BigDecimal("100800.00"), LocalDate.of(2025, 10, 10), "Patrocinio", "Kauê"));
        save(new Transaction(null, "receita", "Patrocínio", new BigDecimal("35000.00"), LocalDate.of(2025, 10, 12), "Patrocinio", "Nike"));
        save(new Transaction(null, "despesa", "Salário Comissão Técnica", new BigDecimal("52350.00"), LocalDate.of(2025, 10, 12), "Folha Salarial", "Administrador"));
    }

    // [C/U] Salva ou Atualiza uma transação
    public Transaction save(Transaction transaction) {
        // Validação básica
        if (transaction.getValue() == null || transaction.getValue().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da transação deve ser positivo.");
        }

        if (transaction.getId() == null) {
            // Novo registro (CREATE)
            transaction.setId(idCounter.incrementAndGet());
            storage.add(transaction);
        } else {
            // Atualizar registro (UPDATE)
            storage.stream()
                    .filter(t -> t.getId().equals(transaction.getId()))
                    .findFirst()
                    .ifPresent(existing -> {
                        existing.setType(transaction.getType());
                        existing.setDescription(transaction.getDescription());
                        existing.setValue(transaction.getValue());
                        existing.setDate(transaction.getDate());
                        existing.setCategory(transaction.getCategory());
                        existing.setResponsible(transaction.getResponsible());
                    });
        }
        return transaction;
    }

    // [R] Lista todas as transações
    public List<Transaction> findAll() {
        return new ArrayList<>(storage);
    }

    // [D] Deleta uma transação por ID
    public void deleteById(Long id) {
        storage.removeIf(t -> t.getId().equals(id));
    }

    // [R - Resumo] Calcula o Saldo Consolidado
    public Map<String, BigDecimal> calculateSummary() {
        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;

        for (Transaction t : storage) {
            if ("receita".equalsIgnoreCase(t.getType())) {
                totalReceitas = totalReceitas.add(t.getValue());
            } else if ("despesa".equalsIgnoreCase(t.getType())) {
                totalDespesas = totalDespesas.add(t.getValue());
            }
        }

        BigDecimal saldoAtual = totalReceitas.subtract(totalDespesas);

        Map<String, BigDecimal> summary = new HashMap<>();
        summary.put("saldoAtual", saldoAtual.setScale(2, RoundingMode.HALF_UP));
        summary.put("totalReceitas", totalReceitas.setScale(2, RoundingMode.HALF_UP));
        summary.put("totalDespesas", totalDespesas.setScale(2, RoundingMode.HALF_UP));

        return summary;
    }
}

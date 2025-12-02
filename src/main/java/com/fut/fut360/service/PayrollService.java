package com.fut.fut360.service;

import com.fut.fut360.Model.PayrollItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PayrollService {

    private final List<PayrollItem> storage = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public PayrollService() {
        // Dados de exemplo
        save(new PayrollItem(null, "Atleta 1", "Atacante", new BigDecimal("15000.00"), "pago", LocalDate.now().minusDays(5)));
        save(new PayrollItem(null, "Funcionário 1", "Marketing", new BigDecimal("3500.00"), "pendente", LocalDate.now().plusDays(5)));
    }

    // [C/U] Salva ou Atualiza
    public PayrollItem save(PayrollItem item) {
        if (item.getId() == null) {
            item.setId(idCounter.incrementAndGet());
            storage.add(item);
        } else {
            storage.stream()
                    .filter(p -> Objects.equals(p.getId(), item.getId()))
                    .findFirst()
                    .ifPresent(existing -> {
                        existing.setName(item.getName());
                        existing.setRole(item.getRole());
                        existing.setValue(item.getValue());
                        existing.setStatus(item.getStatus());
                        existing.setPaymentDate(item.getPaymentDate());
                    });
        }
        return item;
    }

    // [R] Lista tudo
    public List<PayrollItem> findAll() {
        return new ArrayList<>(storage);
    }

    // [D] Deleta
    public void deleteById(Long id) {
        storage.removeIf(p -> Objects.equals(p.getId(), id));
    }

    // [U] Atualiza o status
    public void updateStatus(Long id, String newStatus) {
        storage.stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst()
                .ifPresent(p -> p.setStatus(newStatus));
    }

    // [R - Resumo] Relatório de Custos Mensais (REQUISITO 6)
    public Map<String, BigDecimal> calculatePayrollSummary() {
        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal totalPending = BigDecimal.ZERO;

        for (PayrollItem item : storage) {
            if (item.getValue() != null) {
                totalCost = totalCost.add(item.getValue());
                if ("pendente".equalsIgnoreCase(item.getStatus())) {
                    totalPending = totalPending.add(item.getValue());
                }
            }
        }

        Map<String, BigDecimal> resumo = new HashMap<>();
        resumo.put("totalCost", totalCost.setScale(2, RoundingMode.HALF_UP));
        resumo.put("totalPending", totalPending.setScale(2, RoundingMode.HALF_UP));
        return resumo;
    }
}
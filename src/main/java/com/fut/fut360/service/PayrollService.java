package com.fut.fut360.service;

import com.fut.fut360.Model.PayrollItem;
import com.fut.fut360.Repository.PayrollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PayrollService {

    @Autowired
    private PayrollRepository repository; // <--- OBRIGATÓRIO

    // [C/U] Salva no Banco
    public PayrollItem save(PayrollItem item) {
        return repository.save(item); // <--- TEM QUE SER ASSIM
    }

    // [R] Busca do Banco
    public List<PayrollItem> findAll() {
        return repository.findAll();
    }

    // [D] Deleta do Banco
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public Map<String, BigDecimal> calculatePayrollSummary() {
        List<PayrollItem> items = repository.findAll(); // Busca do banco para somar

        BigDecimal totalCost = BigDecimal.ZERO;
        BigDecimal totalPending = BigDecimal.ZERO;

        for (PayrollItem item : items) {
            if (item.getValue() != null) {
                totalCost = totalCost.add(item.getValue());
                if ("pendente".equalsIgnoreCase(item.getStatus())) {
                    totalPending = totalPending.add(item.getValue());
                }
            }
        }
        Map<String, BigDecimal> summary = new HashMap<>();
        summary.put("totalCost", totalCost);
        summary.put("totalPending", totalPending);
        return summary;
    }
}
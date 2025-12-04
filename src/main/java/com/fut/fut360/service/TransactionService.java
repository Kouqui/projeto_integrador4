package com.fut.fut360.service; // Verifique se o pacote é .service ou .Service

import com.fut.fut360.Model.Transaction;
import com.fut.fut360.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository; // <--- Conexão real com o MySQL

    // [C/U] Salva ou Atualiza no Banco
    public Transaction save(Transaction transaction) {
        // O repositório decide sozinho se é INSERT ou UPDATE baseado no ID
        return repository.save(transaction);
    }

    // [R] Busca tudo do Banco
    public List<Transaction> findAll() {
        return repository.findAll();
    }

    // [D] Deleta do Banco
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // [R - Resumo] Calcula os totais usando os dados do Banco
    public Map<String, BigDecimal> calculateSummary() {
        // Busca dados reais do banco
        List<Transaction> allTransactions = repository.findAll();

        BigDecimal totalReceitas = BigDecimal.ZERO;
        BigDecimal totalDespesas = BigDecimal.ZERO;

        for (Transaction t : allTransactions) {
            // Verifica se o valor não é nulo para evitar erros
            if (t.getValue() != null) {
                if ("receita".equalsIgnoreCase(t.getType())) {
                    totalReceitas = totalReceitas.add(t.getValue());
                } else if ("despesa".equalsIgnoreCase(t.getType())) {
                    totalDespesas = totalDespesas.add(t.getValue());
                }
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
package com.fut.fut360.service;

import com.fut.fut360.Model.Contract;
import com.fut.fut360.Repository.ContractRepository; // <--- NÃO ESQUEÇA!
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ContractService {

    @Autowired
    private ContractRepository repository; // <--- INJEÇÃO DO REPOSITÓRIO

    public Contract save(Contract contract) {
        return repository.save(contract);
    }

    public List<Contract> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    // Lógica de alerta (Requisito 7)
    public long getDaysUntilExpiration(LocalDate endDate) {
        if (endDate == null) return 0;
        return ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }
}
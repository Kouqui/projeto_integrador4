package com.fut.fut360.service;

import com.fut.fut360.model.Contract;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ContractService {

    private final List<Contract> storage = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(0);

    public ContractService() {
        // Dados de exemplo (com alerta de vencimento)
        save(new Contract(null, "Atleta Craque", 25, "Atacante", LocalDate.now().minusYears(1), LocalDate.now().plusMonths(2), "link/craque.pdf"));
        save(new Contract(null, "Goleiro Reserva", 30, "Goleiro", LocalDate.now().minusYears(3), LocalDate.now().plusMonths(8), "link/goleiro.pdf"));
        save(new Contract(null, "Jovem Promessa", 18, "Meio Campo", LocalDate.now().minusMonths(6), LocalDate.now().minusDays(10), "link/jovem.pdf")); // Vencido
    }

    // [C/U] Salva ou Atualiza
    public Contract save(Contract contract) {
        if (contract.getId() == null) {
            contract.setId(idCounter.incrementAndGet());
            storage.add(contract);
        } else {
            storage.stream()
                    .filter(c -> Objects.equals(c.getId(), contract.getId()))
                    .findFirst()
                    .ifPresent(existing -> {
                        existing.setName(contract.getName());
                        existing.setAge(contract.getAge());
                        existing.setPosition(contract.getPosition());
                        existing.setStartDate(contract.getStartDate());
                        existing.setEndDate(contract.getEndDate());
                        existing.setDocumentUrl(contract.getDocumentUrl());
                    });
        }
        return contract;
    }

    // [R] Lista tudo
    public List<Contract> findAll() {
        return new ArrayList<>(storage);
    }

    // [D] Deleta
    public void deleteById(Long id) {
        storage.removeIf(c -> Objects.equals(c.getId(), id));
    }

    // Lógica para o REQUISITO 7: Cálculo de dias até a expiração
    public long getDaysUntilExpiration(LocalDate endDate) {
        // Retorna a diferença em dias entre hoje e a data de término
        return ChronoUnit.DAYS.between(LocalDate.now(), endDate);
    }
}
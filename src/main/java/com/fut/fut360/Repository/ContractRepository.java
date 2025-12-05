package com.fut.fut360.Repository;

import com.fut.fut360.Model.Contract; // ou .Model.Contract
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    // Esse comando mágico busca só quem tem salário (ignora os NULL)
    List<Contract> findBySalarioIsNotNull();
}
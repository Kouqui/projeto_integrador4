//Criação pelo autor: Jefferson Andrey Dias Cardoso - Ra: 24017498

package com.fut.fut360.Repository;

import com.fut.fut360.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Pronto! O Spring já cria os métodos save, findAll, deleteById automaticamente.
}
package com.fut.fut360.repository;

import com.fut.fut360.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Busca transações por tipo (receita/despesa)
    List<Transaction> findByType(String type);

    // Busca transações por categoria
    List<Transaction> findByCategory(String category);

    // Busca transações entre duas datas
    List<Transaction> findByDateBetween(LocalDate inicio, LocalDate fim);

    // Calcula o total de receitas
    @Query("SELECT COALESCE(SUM(t.value), 0) FROM Transaction t WHERE t.type = 'receita'")
    BigDecimal calcularTotalReceitas();

    // Calcula o total de despesas
    @Query("SELECT COALESCE(SUM(t.value), 0) FROM Transaction t WHERE t.type = 'despesa'")
    BigDecimal calcularTotalDespesas();

    // Calcula o saldo (receitas - despesas)
    @Query("SELECT COALESCE(SUM(CASE WHEN t.type = 'receita' THEN t.value ELSE -t.value END), 0) FROM Transaction t")
    BigDecimal calcularSaldo();
}

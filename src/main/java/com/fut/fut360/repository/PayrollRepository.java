package com.fut.fut360.repository;

import com.fut.fut360.model.PayrollItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PayrollRepository extends JpaRepository<PayrollItem, Long> {

    // Busca itens da folha por status
    List<PayrollItem> findByStatus(String status);

    // Busca itens da folha entre duas datas de pagamento
    List<PayrollItem> findByPaymentDateBetween(LocalDate inicio, LocalDate fim);

    // Busca o próximo pagamento pendente
    PayrollItem findFirstByStatusOrderByPaymentDateAsc(String status);

    // Calcula o total da folha salarial do mês
    @Query("SELECT COALESCE(SUM(p.value), 0) FROM PayrollItem p WHERE p.paymentDate BETWEEN :inicio AND :fim")
    BigDecimal calcularTotalFolhaMes(LocalDate inicio, LocalDate fim);

    // Calcula o total da folha pendente
    @Query("SELECT COALESCE(SUM(p.value), 0) FROM PayrollItem p WHERE p.status = 'pendente'")
    BigDecimal calcularTotalPendente();
}

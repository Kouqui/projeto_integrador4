package com.fut.fut360.Repository;

import com.fut.fut360.Model.PayrollItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollRepository extends JpaRepository<PayrollItem, Long> {
}
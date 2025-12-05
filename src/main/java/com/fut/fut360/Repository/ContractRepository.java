//Criação pelo autor: Jefferson Andrey Dias Cardoso - Ra: 24017498

package com.fut.fut360.Repository;

import com.fut.fut360.Model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Long> {
}

package com.fut.fut360.Repository;
import com.fut.fut360.Model.RegistroCarreira;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistroCarreiraRepository extends JpaRepository<RegistroCarreira, Long> {}
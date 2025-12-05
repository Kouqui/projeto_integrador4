//Criação pelo autor: Kauã Kouqui
package com.fut.fut360.Repository;
import com.fut.fut360.Model.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Long> {}
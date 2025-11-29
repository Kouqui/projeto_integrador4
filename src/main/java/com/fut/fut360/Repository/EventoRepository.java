package com.fut.fut360.Repository; // <--- R MAIÚSCULO (igual sua pasta)

import com.fut.fut360.Model.Evento; // <--- M MAIÚSCULO (importando da sua pasta Model)
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    // Esse arquivo mágico já cria o salvar, deletar e buscar automaticamente!
}
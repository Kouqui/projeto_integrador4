package com.fut.fut360.repository;

import com.fut.fut360.Model.Calendario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CalendarioRepository extends JpaRepository<Calendario, Long> {

    // Busca os próximos 3 eventos após uma data específica, ordenados por data/hora
    List<Calendario> findTop3ByDataHoraAfterOrderByDataHoraAsc(LocalDateTime dataHora);

    // Busca os próximos 5 eventos após uma data específica, ordenados por data/hora
    List<Calendario> findTop5ByDataHoraAfterOrderByDataHoraAsc(LocalDateTime dataHora);

    // Busca eventos por categoria
    List<Calendario> findByCategoria(String categoria);

    // Busca eventos por tipo
    List<Calendario> findByTipo(String tipo);

    // Busca todos os eventos entre duas datas
    List<Calendario> findByDataHoraBetweenOrderByDataHoraAsc(LocalDateTime inicio, LocalDateTime fim);
}

//CRIAÇÃO PELO AUTOR: KAUÊ FARIAS LOURENÇO RA:24788788

package com.fut.fut360.Repository;

import com.fut.fut360.Model.EventoEstatistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoEstatisticaRepository extends JpaRepository<EventoEstatistica, Long> {
    // Busca estatísticas pelo ID do evento
    Optional<EventoEstatistica> findByEventId(Long eventId);
}

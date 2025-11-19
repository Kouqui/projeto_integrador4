package com.fut.fut360.repository;

import com.fut.fut360.model.Atleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Long> {

    // Busca os top 5 atletas ordenados pela nota geral do treino (DESC)
    List<Atleta> findTop5ByOrderByTreinoNotaGeralDesc();

    // Busca atletas por categoria
    List<Atleta> findByCategory(String category);

    // Busca atletas por posição
    List<Atleta> findByPosition(String position);
}

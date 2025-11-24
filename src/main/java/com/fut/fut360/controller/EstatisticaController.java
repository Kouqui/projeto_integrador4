package com.fut.fut360.controller;

import com.fut.fut360.Model.Atleta;
import com.fut.fut360.Model.EstatisticaTemporada;
import com.fut.fut360.Repository.AtletaRepository;
import com.fut.fut360.Repository.EstatisticaTemporadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class EstatisticaController {

    @Autowired
    private EstatisticaTemporadaRepository estatisticaRepository;

    @Autowired
    private AtletaRepository atletaRepository;

    @PostMapping("/estatisticas/salvar")
    public String salvarEstatistica(@ModelAttribute("estatisticaNova") EstatisticaTemporada formStats,
                                    @RequestParam("atletaId") Long atletaId) {

        Optional<Atleta> atletaOptional = atletaRepository.findById(atletaId);

        if (atletaOptional.isPresent()) {
            Atleta atleta = atletaOptional.get();
            EstatisticaTemporada statsParaSalvar;

            // 1. VERIFICA SE É UPDATE (Se tem ID)
            if (formStats.getId() != null) {
                Optional<EstatisticaTemporada> existente = estatisticaRepository.findById(formStats.getId());
                if (existente.isPresent()) {
                    // É uma edição: Atualiza os campos do objeto existente
                    statsParaSalvar = existente.get();
                    statsParaSalvar.setTemporada(formStats.getTemporada());
                    statsParaSalvar.setGames(formStats.getGames());
                    statsParaSalvar.setGoals(formStats.getGoals());
                    statsParaSalvar.setAssists(formStats.getAssists());
                    statsParaSalvar.setMinutes(formStats.getMinutes());
                    statsParaSalvar.setYellow(formStats.getYellow());
                    statsParaSalvar.setRed(formStats.getRed());
                } else {
                    // ID não encontrado (raro), cria novo
                    statsParaSalvar = formStats;
                }
            } else {
                // 2. É CREATE (Não tem ID)
                statsParaSalvar = formStats;
            }

            // Garante valores padrão se estiverem vazios
            if (statsParaSalvar.getTemporada() == null || statsParaSalvar.getTemporada().isEmpty()) {
                statsParaSalvar.setTemporada(String.valueOf(LocalDate.now().getYear()));
            }

            // Amarra ao atleta e salva
            statsParaSalvar.setAtleta(atleta);
            estatisticaRepository.save(statsParaSalvar);
        }

        return "redirect:/atletas";
    }
}
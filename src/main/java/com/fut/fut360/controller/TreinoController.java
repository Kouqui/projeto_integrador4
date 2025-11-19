package com.fut.fut360.controller;

import com.fut.fut360.Model.Atleta;
import com.fut.fut360.Model.Treino;
import com.fut.fut360.Repository.AtletaRepository;
import com.fut.fut360.Repository.TreinoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class TreinoController {

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private AtletaRepository atletaRepository;

    @PostMapping("/treino/salvar")
    public String salvarNovoTreino(@ModelAttribute("treinoNovo") Treino treinoNovo,
                                   @RequestParam("atletaId") Long atletaId) {

        // 1. Encontra o "pai" (Atleta) no banco de dados
        Optional<Atleta> atletaOptional = atletaRepository.findById(atletaId);

        if (atletaOptional.isPresent()) {
            Atleta atleta = atletaOptional.get();

            // 2. Define a data do treino
            treinoNovo.setDataTreino(LocalDate.now());

            // 3. "Amarra" o treino ao atleta
            treinoNovo.setAtleta(atleta);

            // 4. Salva o novo treino
            treinoRepository.save(treinoNovo);
        }

        return "redirect:/atletas";
    }
}
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

@Controller // ESSA ANOTAÇÃO É OBRIGATÓRIA
public class TreinoController {

    @Autowired
    private TreinoRepository treinoRepository;

    @Autowired
    private AtletaRepository atletaRepository;

    // ESTE MAPEAMENTO TEM QUE SER EXATAMENTE IGUAL AO DO FORMULÁRIO
    @PostMapping("/treino/salvar")
    public String salvarNovoTreino(@ModelAttribute("treinoNovo") Treino treinoNovo,
                                   @RequestParam("atletaId") Long atletaId) {

        // Busca o atleta pai pelo ID
        Optional<Atleta> atletaOptional = atletaRepository.findById(atletaId);

        if (atletaOptional.isPresent()) {
            Atleta atleta = atletaOptional.get();

            // Configura o treino
            treinoNovo.setDataTreino(LocalDate.now());
            treinoNovo.setAtleta(atleta); // Amarra ao atleta

            // Salva no banco
            treinoRepository.save(treinoNovo);
        }

        return "redirect:/atletas";
    }
}
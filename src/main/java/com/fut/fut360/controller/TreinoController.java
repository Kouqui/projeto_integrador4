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
    @Autowired private TreinoRepository treinoRepository;
    @Autowired private AtletaRepository atletaRepository;

    @PostMapping("/treino/salvar")
    public String salvarNovoTreino(@ModelAttribute("treinoNovo") Treino treinoRecebido,
                                   @RequestParam("atletaId") Long atletaId) {

        Optional<Atleta> atletaOptional = atletaRepository.findById(atletaId);

        if (atletaOptional.isPresent()) {
            Atleta atleta = atletaOptional.get();
            Treino treinoParaSalvar;

            // LÓGICA DE ATUALIZAÇÃO (UPDATE)
            // Se o formulário enviou um ID, buscamos o treino existente para atualizar
            if (treinoRecebido.getId() != null) {
                Optional<Treino> treinoExistente = treinoRepository.findById(treinoRecebido.getId());
                if (treinoExistente.isPresent()) {
                    treinoParaSalvar = treinoExistente.get();
                    // Atualizamos os campos com os novos valores do formulário
                    treinoParaSalvar.setTreinoNotaTatica(treinoRecebido.getTreinoNotaTatica());
                    treinoParaSalvar.setTreinoNotaFisica(treinoRecebido.getTreinoNotaFisica());
                    treinoParaSalvar.setTreinoPassesCertos(treinoRecebido.getTreinoPassesCertos());
                    treinoParaSalvar.setTreinoPassesTotal(treinoRecebido.getTreinoPassesTotal());
                    treinoParaSalvar.setTreinoChutesCertos(treinoRecebido.getTreinoChutesCertos());
                    treinoParaSalvar.setTreinoChutesTotal(treinoRecebido.getTreinoChutesTotal());
                    treinoParaSalvar.setTreinoDriblesCertos(treinoRecebido.getTreinoDriblesCertos());
                    treinoParaSalvar.setTreinoDriblesTotal(treinoRecebido.getTreinoDriblesTotal());
                    treinoParaSalvar.setTreinoDesarmes(treinoRecebido.getTreinoDesarmes());
                    treinoParaSalvar.setTreinoInterceptacoes(treinoRecebido.getTreinoInterceptacoes());
                    treinoParaSalvar.setTreinoKmPercorridos(treinoRecebido.getTreinoKmPercorridos());
                    treinoParaSalvar.setTreinoVelocidadeMax(treinoRecebido.getTreinoVelocidadeMax());
                    treinoParaSalvar.setTreinoSprints(treinoRecebido.getTreinoSprints());
                } else {
                    // Se não achou (estranho), cria novo
                    treinoParaSalvar = treinoRecebido;
                    treinoParaSalvar.setDataTreino(LocalDate.now());
                }
            } else {
                // LÓGICA DE CRIAÇÃO (INSERT)
                treinoParaSalvar = treinoRecebido;
                treinoParaSalvar.setDataTreino(LocalDate.now());
            }

            treinoParaSalvar.setAtleta(atleta);
            treinoRepository.save(treinoParaSalvar);
        }
        return "redirect:/atletas";
    }
}
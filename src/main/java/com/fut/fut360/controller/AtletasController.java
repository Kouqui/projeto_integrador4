package com.fut.fut360.controller;

import com.fut.fut360.model.Atleta;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AtletasController {

    @GetMapping("/atletas")
    public String exibirPaginaAtletas(Model model) {

        List<Atleta> listaDeAtletas = new ArrayList<>();

        listaDeAtletas.add(new Atleta(
                "Neymar Jr.", "Atacante", "Profissional",
                "https://img.a.transfermarkt.technology/portrait/header/68290-1692601435.jpg?lm=1",
                "Neymar da Silva Santos Júnior", "05/02/1992", 33, "Brasileiro", "1,75 m", "68 kg", "Ambidestro",
                10, 850, 8, 5, 2, 0,
                "Recuperar 100% da forma física pós-lesão.", "Participar de 15 gols na temporada (G+A).",
                "25/10: Demonstrou melhora na intensidade. Foco em fortalecimento.",
                "R$ 40.000.000,00", "01/08/2023", "31/07/2025", "R$ 200.000.000,00",
                // Dados do "SofaScore de Treino"
                8.2, // Nota Geral
                8.0, 9.0, 7.5, // Tática, Técnica, Físico
                52, 55, // Passes
                7, 10, // Chutes
                8, 12, // Dribles
                2, 4, // Desarmes, Interceptações
                7.2, 31.5, 8 // Km, Vel. Max, Sprints
        ));

        listaDeAtletas.add(new Atleta(
                "Lionel Messi", "Atacante", "Profissional",
                "https://img.a.transfermarkt.technology/portrait/header/28003-1740766555.jpg?lm=1",
                "Lionel Andrés Messi Cuccittini", "24/06/1987", 38, "Argentino", "1,70 m", "67 kg", "Esquerdo",
                15, 1350, 12, 10, 1, 0,
                "Liderar a equipe nos playoffs.", "Manter média de participação em gol > 1.0 por jogo.",
                "24/10: Excelente visão de jogo no último treino. Dosando carga física.",
                "R$ 35.000.000,00", "05/07/2023", "31/12/2025", "R$ 150.000.000,00",
                // Dados do "SofaScore de Treino"
                9.4, // Nota Geral
                9.5, 9.8, 8.0, // Tática, Técnica, Físico
                68, 70, // Passes
                8, 9, // Chutes
                15, 16, // Dribles
                1, 2, // Desarmes, Interceptações
                6.8, 28.0, 5 // Km, Vel. Max, Sprints
        ));

        listaDeAtletas.add(new Atleta(
                "Cristiano Ronaldo", "Atacante", "Profissional",
                "https://img.a.transfermarkt.technology/portrait/header/8198-1748102259.jpg?lm=1",
                "Cristiano Ronaldo dos Santos Aveiro", "05/02/1985", 40, "Português", "1,87 m", "83 kg", "Destro",
                14, 1260, 18, 4, 3, 0,
                "Ser artilheiro da temporada (Meta: 30+ gols).", "Manter índice de 90% nos treinos físicos.",
                "23/10: Foco absoluto em finalização. Cobrou 50 faltas após o treino.",
                "R$ 38.000.000,00", "01/01/2023", "30/06/2025", "R$ 180.000.000,00",
                // Dados do "SofaScore de Treino"
                9.0, // Nota Geral
                8.5, 9.0, 9.5, // Tática, Técnica, Físico
                30, 32, // Passes
                10, 12, // Chutes
                5, 7, // Dribles
                0, 1, // Desarmes, Interceptações
                8.5, 33.2, 14 // Km, Vel. Max, Sprints
        ));

        model.addAttribute("atletas", listaDeAtletas);

        // Não vamos adicionar o "atletaNovo" pois não há banco para salvar

        return "atletas";
    }

    // Sem o @PostMapping("/atletas/salvar")
}
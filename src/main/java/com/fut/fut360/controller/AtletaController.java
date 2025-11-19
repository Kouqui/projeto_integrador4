package com.fut.fut360.controller;

import com.fut.fut360.model.Atleta;
import com.fut.fut360.repository.AtletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute; // Importar
import org.springframework.web.bind.annotation.PostMapping;   // Importar

import java.util.List;

@Controller
public class AtletaController {

    @Autowired
    private AtletaRepository atletaRepository;

    @GetMapping("/atletas")
    public String listarAtletas(Model model) {
        List<Atleta> atletas = atletaRepository.findAll();
        model.addAttribute("atletas", atletas);
        // Adiciona um objeto vazio para o formulário preencher
        model.addAttribute("novoAtleta", new Atleta());
        return "Atletas";
    }

    // NOVO MÉTODO PARA SALVAR
    @PostMapping("/atletas/salvar")
    public String salvarAtleta(@ModelAttribute Atleta atleta) {
        atletaRepository.save(atleta);
        return "redirect:/atletas"; // Recarrega a página para mostrar o novo atleta
    }
}
package com.fut.fut360.controller;

import com.fut.fut360.Model.Calendario;
import com.fut.fut360.repository.CalendarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CalendarioController {

    @Autowired
    private CalendarioRepository calendarioRepository;

    @GetMapping("/calendario")
    public String exibirPaginaCalendario(Model model) {
        // Busca todos os eventos do banco
        List<Calendario> eventos = calendarioRepository.findAll();

        // Busca o mês e ano atual
        LocalDateTime agora = LocalDateTime.now();
        int mesAtual = agora.getMonthValue();
        int anoAtual = agora.getYear();

        model.addAttribute("eventos", eventos);
        model.addAttribute("mesAtual", mesAtual);
        model.addAttribute("anoAtual", anoAtual);
        model.addAttribute("dataAtual", agora.toLocalDate());

        return "Calendario"; // Corresponde ao arquivo Calendario.html
    }

    @GetMapping("/api/calendario/eventos")
    @ResponseBody
    public ResponseEntity<List<Calendario>> buscarEventos() {
        List<Calendario> eventos = calendarioRepository.findAll();
        return ResponseEntity.ok(eventos);
    }

    @PostMapping("/api/calendario/eventos")
    @ResponseBody
    public ResponseEntity<Calendario> criarEvento(@RequestBody Calendario evento) {
        Calendario novoEvento = calendarioRepository.save(evento);
        return ResponseEntity.ok(novoEvento);
    }
}
package com.fut.fut360.controller;

// --- ATENÇÃO AQUI: IMPORTS COM LETRA MAIÚSCULA IGUAL SUAS PASTAS ---
import com.fut.fut360.Model.Evento;
import com.fut.fut360.Repository.EventoRepository;
// ------------------------------------------------------------------

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/eventos") // O JavaScript chama esse endereço
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    // 1. Listar todos
    @GetMapping
    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    // 2. Salvar ou Editar
    @PostMapping
    public Evento salvar(@RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    // 3. Excluir
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        eventoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
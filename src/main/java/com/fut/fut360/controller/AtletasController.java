package com.fut.fut360.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AtletasController {

    @GetMapping("/atletas")
    public String exibirPaginaAtletas() {
        return "atletas"; // Corresponde ao arquivo atletas.html
    }
}   
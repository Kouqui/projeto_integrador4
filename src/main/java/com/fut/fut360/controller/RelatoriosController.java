package com.fut.fut360.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RelatoriosController {

    @GetMapping("/relatorios")
    public String exibirPaginaRelatorios() {
        return "relatorios"; // Corresponde ao arquivo relatorios.html
    }
}
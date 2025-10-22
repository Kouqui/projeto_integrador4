package com.fut.fut360.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FinanceiroController {

    @GetMapping("/financeiro")
    public String exibirPaginaFinanceira() {
        return "financeiro"; // Retorna o arquivo financeiro.html
    }
}
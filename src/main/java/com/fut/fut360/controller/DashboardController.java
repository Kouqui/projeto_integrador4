package com.fut.fut360.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String exibirDashboard() {
        return "dashboard"; // Retorna o nome do arquivo "dashboard.html"
    }

    // Adicione aqui os métodos para as outras páginas quando for criá-las
    // @GetMapping("/financeiro")
    // public String exibirFinanceiro() {
    //     return "financeiro";
    // }
}
package com.fut.fut360.controller;

import com.fut.fut360.dto.DashboardDTO;
import com.fut.fut360.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String exibirDashboard(Model model) {
        // Busca todos os dados do dashboard
        DashboardDTO dashboardData = dashboardService.getDashboardData();

        // Adiciona os dados ao modelo para serem acessados pelo Thymeleaf
        model.addAttribute("dashboard", dashboardData);

        return "Dashboard"; // Retorna o nome do arquivo "Dashboard.html"
    }

}
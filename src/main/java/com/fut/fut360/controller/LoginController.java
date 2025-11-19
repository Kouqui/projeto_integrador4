package com.fut.fut360.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String exibirPaginaDeLogin() {
        return "login"; // Mude de "Login" para "login"
    }

    @GetMapping("/")
    public String exibirPaginaInicial() {
        return "redirect:/dashboard"; // Este está OK
    }
}
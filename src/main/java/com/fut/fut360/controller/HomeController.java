//CRIAÇÃO PELO AUTOR: KAUÊ FARIAS LOURENÇO RA:24788788

package com.fut.fut360.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    // 1. Tela de Login do ADM (GET)
    @GetMapping("/admin")
    public String exibirLoginAdm() {
        return "login_adm"; // Vai procurar o arquivo login_adm.html
    }

    // 2. Processar o Login (POST)
    @PostMapping("/admin/entrar")
    public String validarLogin(@RequestParam("usuario") String usuario,
                               @RequestParam("senha") String senha,
                               Model model) {

        // Verifica se é o admin oq faz sentido pq so ele pode entrar no servidor
        if ("adm123".equals(usuario) && "123123".equals(senha)) {
            return "redirect:/central"; // Sucesso: Manda para a Central
        }

        // Erro: Volta para o login com mensagem
        model.addAttribute("erro", "Acesso Negado: Credenciais inválidas.");
        return "login_adm";
    }

    // 3. A Central de Comando (Só deve chegar aqui quem logou)
    @GetMapping("/central")
    public String exibirHome() {
        return "home";
    }
}
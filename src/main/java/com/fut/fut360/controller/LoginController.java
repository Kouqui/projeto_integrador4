// A MUDANÇA ESTÁ AQUI. ADICIONAMOS "fut." NO INÍCIO DO PACOTE
package com.fut.fut360.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    /**
     * Este método responde à requisição GET na URL "/login".
     * Ele diz ao Spring para renderizar a página "Login.html" da pasta /templates.
     * @return O nome do template HTML a ser exibido.
     */
    @GetMapping("/login")
    public String exibirPaginaDeLogin() {
        return "Login"; // Retorna o nome do arquivo HTML sem a extensão .html
    }

    /**
     * BÔNUS: Este método faz com que a página de login também seja a página inicial.
     * Assim, o usuário pode acessar tanto por "localhost:8080/" quanto por "localhost:8080/login".
     * @return O nome do template HTML a ser exibido.
     */
    @GetMapping("/")
    public String exibirPaginaInicial() {
        return "Login";
    }
}
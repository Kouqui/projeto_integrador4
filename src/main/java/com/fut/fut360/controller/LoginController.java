//CRIAÇÃO PELO AUTOR: KAUÊ FARIAS LOURENÇO RA:24788788

package com.fut.fut360.controller;

import com.fut.fut360.Model.Usuario;
import com.fut.fut360.Repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. Raiz vai para o Login
    @GetMapping("/")
    public String exibirPaginaInicial() {
        return "redirect:/login";
    }

    // 2. Tela de Login
    @GetMapping("/login")
    public String exibirPaginaDeLogin() {
        return "Login";
    }

    // 3. Processar Login
    @PostMapping("/login/entrar")
    public String validarLogin(@RequestParam("email") String email,
                               @RequestParam("senha") String senha,
                               HttpSession session,
                               Model model) {

        Usuario usuarioBanco = usuarioRepository.findByEmail(email);

        if (usuarioBanco != null && usuarioBanco.getSenha().equals(senha)) {
            session.setAttribute("usuarioLogado", usuarioBanco);
            return "redirect:/dashboard";
        }

        // --- MUDANÇA AQUI ---
        model.addAttribute("erro", "Usuário ou senha incorretos.");

        // Devolvemos o e-mail para o HTML preencher o campo
        model.addAttribute("emailRecuperado", email);
        // --------------------

        return "Login";
    }

    // 4. Logout
    @GetMapping("/logout")
    public String sair(HttpSession session) {
        session.invalidate(); // Mata a sessão
        return "redirect:/login"; // Manda pro login
    }
}
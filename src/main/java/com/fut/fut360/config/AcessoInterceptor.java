package com.fut.fut360.config;

import com.fut.fut360.service.SystemService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AcessoInterceptor implements HandlerInterceptor {

    @Autowired
    private SystemService systemService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Pega a URL que o usuário acessou (ex: /dashboard)
        String uri = request.getRequestURI();

        // Se for uma das nossas páginas, conta +1
        if (uri.equals("/dashboard") || uri.equals("/atletas") ||
                uri.equals("/financeiro") || uri.equals("/calendario")) {
            systemService.registrarAcesso(uri);
            System.out.println("Acesso registrado em: " + uri); // Log no console
        }

        return true; // Deixa a requisição passar
    }
}
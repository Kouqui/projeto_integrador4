//CRIAÇÃO PELO AUTOR: KAUÊ FARIAS LOURENÇO RA:24788788

package com.fut.fut360.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarioController {

    @GetMapping("/calendario")
    public String exibirPaginaCalendario() {
        return "calendario"; // Corresponde ao arquivo calendario.html
    }
}
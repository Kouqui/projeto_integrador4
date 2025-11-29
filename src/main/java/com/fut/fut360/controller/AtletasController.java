package com.fut.fut360.controller;

import com.fut.fut360.Model.Atleta;
import com.fut.fut360.Model.Contrato;
import com.fut.fut360.Model.EstatisticaTemporada;
import com.fut.fut360.Model.Treino;
import com.fut.fut360.Repository.AtletaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class AtletasController {

    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads/";

    @Autowired
    private AtletaRepository atletaRepository;

    @GetMapping("/atletas")
    public String exibirPaginaAtletas(Model Model) {
        List<Atleta> listaDeAtletas = atletaRepository.findAll();
        Model.addAttribute("atletas", listaDeAtletas);

        // Objetos para os formulários
        Model.addAttribute("atletaNovo", new Atleta());
        Model.addAttribute("contratoNovo", new Contrato());
        Model.addAttribute("estatisticaNova", new EstatisticaTemporada());
        Model.addAttribute("treinoNovo", new Treino());

        return "atletas";
    }

    @PostMapping("/atletas/salvar")
    public String salvarNovoAtleta(
            @ModelAttribute("atletaNovo") Atleta atletaNovo,
            @ModelAttribute("contratoNovo") Contrato contratoNovo,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        // 1. Upload
        if (imageFile != null && !imageFile.isEmpty()) {
            String originalFilename = imageFile.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFilename = UUID.randomUUID().toString() + extension;
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY);
            Path filePath = uploadPath.resolve(uniqueFilename);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(imageFile.getInputStream(), filePath);
            atletaNovo.setPhoto(uniqueFilename);
        } else {
            atletaNovo.setPhoto("default.png");
        }

        // 2. Contrato
        contratoNovo.setAtivo(true);
        atletaNovo.addContrato(contratoNovo);

        // 3. Salvar (Sem estatísticas agora)
        atletaRepository.save(atletaNovo);

        return "redirect:/atletas";
    }
}
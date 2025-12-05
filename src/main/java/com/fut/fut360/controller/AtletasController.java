package com.fut.fut360.controller;

import com.fut.fut360.Model.*;
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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Controller
public class AtletasController {

    // Define o caminho da pasta de uploads na raiz do projeto
    public static String UPLOAD_DIRECTORY = "uploads";

    @Autowired
    private AtletaRepository atletaRepository;

    @GetMapping("/atletas")
    public String exibirPaginaAtletas(Model model) {
        List<Atleta> listaDeAtletas = atletaRepository.findAll();
        model.addAttribute("atletas", listaDeAtletas);

        // Envia objetos vazios para o formulário
        model.addAttribute("atletaNovo", new Atleta());
        model.addAttribute("contratoNovo", new Contrato());
        model.addAttribute("treinoNovo", new Treino());
        model.addAttribute("estatisticaNova", new EstatisticaTemporada());

        return "atletas";
    }

    @PostMapping("/atletas/salvar")
    public String salvarNovoAtleta(
            @ModelAttribute("atletaNovo") Atleta atletaNovo,
            @ModelAttribute("contratoNovo") Contrato contratoNovo,
            @ModelAttribute("estatisticaNova") EstatisticaTemporada estatisticaNova,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

        // --- LÓGICA DE SALVAMENTO DA FOTO ---
        if (imageFile != null && !imageFile.isEmpty()) {
            // 1. Gera nome único para não substituir fotos iguais
            String nomeOriginal = imageFile.getOriginalFilename();
            // Pega a extensão (.jpg, .png)
            String extensao = "";
            if (nomeOriginal != null && nomeOriginal.contains(".")) {
                extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
            }
            String nomeArquivoFinal = UUID.randomUUID().toString() + extensao;

            // 2. Cria o caminho e a pasta se não existir
            Path caminhoPasta = Paths.get(UPLOAD_DIRECTORY);
            if (!Files.exists(caminhoPasta)) {
                Files.createDirectories(caminhoPasta);
            }

            // 3. Salva o arquivo na pasta
            Path caminhoArquivo = caminhoPasta.resolve(nomeArquivoFinal);
            Files.copy(imageFile.getInputStream(), caminhoArquivo);

            // 4. Salva APENAS o nome do arquivo no objeto Atleta
            atletaNovo.setPhoto(nomeArquivoFinal);
        } else {
            // Se não enviou foto, define uma padrão (você deve ter essa imagem na pasta uploads)
            atletaNovo.setPhoto("default-player.png");
        }

        // --- RESTANTE DA LÓGICA DE SALVAMENTO ---

        // Amarra contrato
        contratoNovo.setAtivo(true);
        atletaNovo.addContrato(contratoNovo);

        // Amarra estatísticas (se existirem dados)
        if (estatisticaNova.getTemporada() == null || estatisticaNova.getTemporada().isEmpty()) {
            estatisticaNova.setTemporada(String.valueOf(LocalDate.now().getYear()));
        }
        atletaNovo.addEstatistica(estatisticaNova);

        // Salva no banco
        atletaRepository.save(atletaNovo);

        return "redirect:/atletas";
    }
}
package com.fut.fut360.controller;

import com.fut.fut360.Model.Transaction;
import com.fut.fut360.service.RelatoriosService;
import com.fut.fut360.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@CrossOrigin(origins = "*") // Permite acesso externo (útil se o front rodar em porta diferente)
public class RelatoriosController {

    @Autowired
    private RelatoriosService relatoriosService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private com.fut.fut360.Repository.EventoRepository eventoRepository;
    
    // ENDPOINT DE TESTE - Para verificar se está lendo o banco
    @GetMapping("/debug/transacoes")
    public ResponseEntity<List<Transaction>> listarTodasTransacoes() {
        List<Transaction> transacoes = transactionService.findAll();
        System.out.println("Total de transações encontradas: " + transacoes.size());
        return ResponseEntity.ok(transacoes);
    }

    @GetMapping("/fluxo-caixa")
    public ResponseEntity<ByteArrayResource> baixarRelatorioFluxoCaixa(
            @RequestParam String mes,
            @RequestParam String ano
    ) {
        // Chama o serviço que busca no banco e gera o PDF
        byte[] dadosPdf = relatoriosService.gerarRelatorioFluxoCaixa(mes, ano);

        // Define o nome do arquivo para download
        String nomeArquivo = "fluxo_caixa_" + mes + "_" + ano + ".pdf";

        return montarRespostaPdf(dadosPdf, nomeArquivo);
    }

    @GetMapping("/categoria")
    public ResponseEntity<ByteArrayResource> baixarRelatorioCategoria(
            @RequestParam("nome") String categoria
    ) {
        byte[] dadosPdf = relatoriosService.gerarRelatorioCategoria(categoria);
        String nomeArquivo = "relatorio_" + categoria.toLowerCase() + ".pdf";

        return montarRespostaPdf(dadosPdf, nomeArquivo);
    }

    @GetMapping("/eventos")
    public ResponseEntity<?> listarEventos() {
        return ResponseEntity.ok(eventoRepository.findAll());
    }

    @GetMapping("/pos-jogo")
    public ResponseEntity<ByteArrayResource> baixarRelatorioPosJogo(
            @RequestParam Long eventoId
    ) {
        byte[] dadosPdf = relatoriosService.gerarRelatorioPosJogo(eventoId);
        String nomeArquivo = "pos_jogo_evento_" + eventoId + ".pdf";

        return montarRespostaPdf(dadosPdf, nomeArquivo);
    }

    private ResponseEntity<ByteArrayResource> montarRespostaPdf(byte[] dados, String nomeArquivo) {
        ByteArrayResource resource = new ByteArrayResource(dados);

        return ResponseEntity.ok()
                // Define que é um anexo para download
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                // Define o tipo de conteúdo como PDF
                .contentType(MediaType.APPLICATION_PDF)
                // Define o tamanho do arquivo
                .contentLength(dados.length)
                .body(resource);
    }
}
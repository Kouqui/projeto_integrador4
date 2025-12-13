package com.fut.fut360.service;

import com.fut.fut360.Model.Atleta;
import com.fut.fut360.Model.Evento;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class RelatoriosService {

    // Correção do erro de Locale (Java 21)
    private static final Locale LOCALE_BR = Locale.forLanguageTag("pt-BR");

    // Formatador: Converte "2025-10-20" para "20/10/2025"
    private static final DateTimeFormatter FORMATO_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy", LOCALE_BR);

    public void gerarRelatorioAtletas(List<Atleta> atletas, String caminhoArquivo) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            document.open();

            // Título
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLUE);
            Paragraph titulo = new Paragraph("Relatório de Atletas - Fut360", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            // Tabela
            PdfPTable table = new PdfPTable(4); // 4 Colunas
            table.setWidthPercentage(100);

            // Cabeçalho
            adicionarCabecalho(table, "Nome");
            adicionarCabecalho(table, "Posição");
            adicionarCabecalho(table, "Idade");
            adicionarCabecalho(table, "Categoria");

            // Dados
            for (Atleta a : atletas) {
                table.addCell(a.getNome());
                table.addCell(a.getPosicao());
                table.addCell(String.valueOf(a.getIdade()));
                table.addCell(a.getCategoria());
            }

            document.add(table);
            System.out.println("PDF de Atletas gerado em: " + caminhoArquivo);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public void gerarRelatorioEventos(List<Evento> eventos, String caminhoArquivo) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(caminhoArquivo));
            document.open();

            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.DARK_GRAY);
            Paragraph titulo = new Paragraph("Agenda de Eventos", fontTitulo);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(20);
            document.add(titulo);

            PdfPTable table = new PdfPTable(5); // Data, Hora, Tipo, Título, Local
            table.setWidthPercentage(100);

            adicionarCabecalho(table, "Data");
            adicionarCabecalho(table, "Hora");
            adicionarCabecalho(table, "Tipo");
            adicionarCabecalho(table, "Título");
            adicionarCabecalho(table, "Local");

            for (Evento evt : eventos) {
                // --- CORREÇÃO DO ERRO DE DATA ---
                // O modelo novo guarda a data como String "YYYY-MM-DD".
                // Precisamos converter para Data e depois formatar para "DD/MM/YYYY"
                String dataFormatada = evt.getData();
                try {
                    LocalDate data = LocalDate.parse(evt.getData()); // Lê "2025-10-20"
                    dataFormatada = data.format(FORMATO_BR);         // Vira "20/10/2025"
                } catch (Exception e) {
                    // Se der erro (ex: data vazia), mantém a string original
                    dataFormatada = evt.getData();
                }

                table.addCell(dataFormatada);
                table.addCell(evt.getHoraInicio());
                table.addCell(evt.getTipo());
                table.addCell(evt.getTitulo());
                table.addCell(evt.getLocal());
            }

            document.add(table);
            System.out.println("PDF de Eventos gerado em: " + caminhoArquivo);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    private void adicionarCabecalho(PdfPTable table, String texto) {
        PdfPCell header = new PdfPCell();
        header.setBackgroundColor(BaseColor.LIGHT_GRAY);
        header.setBorderWidth(2);
        header.setPhrase(new Phrase(texto));
        table.addCell(header);
    }
}
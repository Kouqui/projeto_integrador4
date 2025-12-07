package com.fut.fut360.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfGenerator {

    public static byte[] gerarPdfSimples(String conteudo) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Usa fonte com suporte a caracteres especiais (incluindo acentos)
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            Font fonteMono = new Font(bf, 10, Font.NORMAL);

            // Adiciona margem ao documento
            document.setMargins(30, 30, 30, 30);

            // Divide o conteúdo por quebras de linha e adiciona cada parágrafo
            String[] linhas = conteudo.split("\n");
            for (String linha : linhas) {
                Paragraph paragrafo = new Paragraph(linha, fonteMono);
                paragrafo.setAlignment(Element.ALIGN_LEFT);
                document.add(paragrafo);
            }

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
            // Retorna array vazio em caso de erro
            return new byte[0];
        }

        return out.toByteArray();
    }
}
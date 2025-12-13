package com.fut.fut360.service;

import com.fut.fut360.Model.Atleta;
import com.fut.fut360.util.ConexaoFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class AtletaDAO {

    public List<Atleta> listarTodos() {
        List<Atleta> lista = new ArrayList<>();
        // SELECT buscando tudo
        String sql = "SELECT nome_curto, posicao, data_nascimento, nacionalidade, altura_cm, peso_kg, pe_dominante, categoria FROM atletas";

        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("[DAO] Buscando dados completos no banco...");

            while (rs.next()) {
                String nome = rs.getString("nome_curto");
                String posicao = rs.getString("posicao");
                Date dataNasc = rs.getDate("data_nascimento");

                // Dados extras
                String nac = rs.getString("nacionalidade");
                int altura = rs.getInt("altura_cm");
                double peso = rs.getDouble("peso_kg");
                String pe = rs.getString("pe_dominante");
                String cat = rs.getString("categoria");

                int idade = 0;
                if (dataNasc != null) {
                    idade = Period.between(dataNasc.toLocalDate(), LocalDate.now()).getYears();
                }

                // Cria o objeto com TUDO preenchido
                lista.add(new Atleta(nome, posicao, idade, 0.0, nac, altura, peso, pe, cat));
            }
        } catch (SQLException e) {
            System.err.println("[ERRO DAO] Falha ao listar: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public boolean salvar(Atleta atleta) {
        // INSERT completo (8 campos + nome_completo repetido)
        String sql = "INSERT INTO atletas (nome_completo, nome_curto, posicao, data_nascimento, nacionalidade, altura_cm, peso_kg, pe_dominante, categoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, atleta.getNome()); // nome_completo
            stmt.setString(2, atleta.getNome()); // nome_curto
            stmt.setString(3, atleta.getPosicao());
            stmt.setDate(4, Date.valueOf(LocalDate.now().minusYears(atleta.getIdade())));

            // Novos campos
            stmt.setString(5, atleta.getNacionalidade());
            stmt.setInt(6, atleta.getAlturaCm());
            stmt.setDouble(7, atleta.getPesoKg());
            stmt.setString(8, atleta.getPeDominante());
            stmt.setString(9, atleta.getCategoria());

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("[DAO] Atleta COMPLETO salvo: " + atleta.getNome());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("[ERRO DAO] Falha ao salvar: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
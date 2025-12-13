package com.fut.fut360.service;

import com.fut.fut360.Model.Evento;
import com.fut.fut360.util.ConexaoFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    public List<Evento> listarTodos() {
        List<Evento> lista = new ArrayList<>();
        // Ordenado pela data para mostrar o mais recente primeiro
        String sql = "SELECT titulo, data_evento, hora_inicio, hora_fim, tipo, categoria, local, adversario, descricao FROM eventos ORDER BY data_evento DESC";

        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                // Tratamento de NULL para evitar erro
                String adv = rs.getString("adversario");
                if (adv == null) adv = "-";

                lista.add(new Evento(
                        rs.getString("titulo"),
                        rs.getString("data_evento"), // O driver converte DATE pra String automaticamente
                        rs.getString("hora_inicio"),
                        rs.getString("hora_fim"),
                        rs.getString("tipo"),
                        rs.getString("categoria"),
                        rs.getString("local"),
                        adv,
                        rs.getString("descricao")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean salvar(Evento evt) {
        String sql = "INSERT INTO eventos (titulo, data_evento, hora_inicio, hora_fim, tipo, categoria, local, adversario, descricao) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexaoFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, evt.getTitulo());
            stmt.setString(2, evt.getData());       // O MySQL aceita string "YYYY-MM-DD"
            stmt.setString(3, evt.getHoraInicio()); // O MySQL aceita string "HH:MM"
            stmt.setString(4, evt.getHoraFim());
            stmt.setString(5, evt.getTipo());
            stmt.setString(6, evt.getCategoria());
            stmt.setString(7, evt.getLocal());
            stmt.setString(8, evt.getAdversario());
            stmt.setString(9, evt.getDescricao());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("[ERRO EVENTO] " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
package com.fut.fut360.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoFactory {

    // URL Montada com seus dados (Host + Porta + Banco + SSL obrigatório da Aiven)
    private static final String URL = "jdbc:mysql://projeto-integrador-4-fut360.k.aivencloud.com:13719/defaultdb?ssl-mode=REQUIRED";

    // Usuário
    private static final String USER = "avnadmin";

    // Senha CORRETA (Copiada da sua mensagem)
    private static final String PASS = "AVNS_QA6idVCj93xh8ZCO6g9";

    public static Connection conectar() throws SQLException {
        try {
            // Força o carregamento do driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado no projeto!", e);
        } catch (SQLException e) {
            System.err.println("Erro de Conexão com o Banco: " + e.getMessage());
            throw e;
        }
    }
}
package com.fut.fut360.comunicados;

public class PedidoLogin extends Comunicado {
    private String usuario;
    private String senha;

    public PedidoLogin(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public String getUsuario() { return usuario; }
    public String getSenha() { return senha; }
}
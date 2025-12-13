package com.fut.fut360.servidor;

import com.fut.fut360.rede.Parceiro;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    public static final String PORTA_PADRAO = "3000";

    public static void main(String[] args) {
        ArrayList<Parceiro> usuarios = new ArrayList<>();

        try {
            ServerSocket pedido = new ServerSocket(Integer.parseInt(PORTA_PADRAO));
            System.out.println("Servidor Fut360 (Java Puro) Ativo na porta " + PORTA_PADRAO);
            System.out.println("Aguardando conexoes...");

            for(;;) {
                Socket conexao = pedido.accept();
                System.out.println("Nova conexao recebida: " + conexao);

                SupervisoraDeConexao supervisora = new SupervisoraDeConexao(conexao, usuarios);
                supervisora.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
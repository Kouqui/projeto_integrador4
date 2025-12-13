package com.fut.fut360.rede;

import com.fut.fut360.comunicados.Comunicado;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Parceiro {

    private Socket conexao;
    private ObjectInputStream receptor;
    private ObjectOutputStream transmissor;

    private Object proximoComunicado = null; // Mudou de Comunicado para Object
    private Semaphore mutEx = new Semaphore(1, true);

    public Parceiro(Socket conexao, ObjectInputStream receptor, ObjectOutputStream transmissor) throws Exception {
        if (conexao == null) throw new Exception("Conexao ausente");
        if (receptor == null) throw new Exception("Receptor ausente");
        if (transmissor == null) throw new Exception("Transmissor ausente");

        this.conexao = conexao;
        this.receptor = receptor;
        this.transmissor = transmissor;
    }

    // AGORA ACEITA QUALQUER COISA (Object), NÃO SÓ COMUNICADO
    public void receba(Object x) throws Exception {
        try {
            this.transmissor.writeObject(x);
            this.transmissor.flush();
        } catch (Exception erro) {
            throw new Exception("Erro de transmissao");
        }
    }

    // AGORA RETORNA QUALQUER COISA (Object)
    public Object envie() throws Exception {
        try {
            if (this.proximoComunicado == null)
                this.proximoComunicado = this.receptor.readObject();
            Object ret = this.proximoComunicado;
            this.proximoComunicado = null;
            return ret;
        } catch (Exception erro) {
            throw new Exception("Erro de recepcao: " + erro.getMessage());
        }
    }

    public void adeus() throws Exception {
        try {
            this.transmissor.close();
            this.receptor.close();
            this.conexao.close();
        } catch (Exception erro) {
            throw new Exception("Erro de desconexao");
        }
    }
}
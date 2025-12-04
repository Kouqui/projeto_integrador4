package com.fut.fut360;

import com.fut.fut360.Model.Usuario;
import com.fut.fut360.Repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Fut360Application {

    public static void main(String[] args) {
        SpringApplication.run(Fut360Application.class, args);
    }

    // --- CRIADOR DE ADMIN AUTOMÁTICO ---
    @Bean
    public CommandLineRunner criarUsuarioAdmin(UsuarioRepository repository) {
        return (args) -> {
            // Verifica se já existe alguém com esse e-mail
            if (repository.findByEmail("admin@fut360.com") == null) {

                // Cria o objeto Usuário
                Usuario admin = new Usuario();
                admin.setNome("Administrador Supremo");
                admin.setEmail("admin@fut360.com");
                admin.setSenha("123123"); // Senha inicial
                admin.setCargo("Admin");

                // Salva no Banco de Dados
                repository.save(admin);

                System.out.println("=========================================");
                System.out.println("✅ USUÁRIO ADMIN CRIADO COM SUCESSO!");
                System.out.println("📧 Login: admin@fut360.com");
                System.out.println("🔑 Senha: 123123");
                System.out.println("=========================================");
            }
        };
    }
}
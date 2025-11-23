package com.fut.fut360.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Pega o caminho absoluto da pasta "uploads"
        // (Ex: "file:///C:/Workspace/projeto_integrador4/uploads/")
        String uploadDir = Paths.get(System.getProperty("user.dir"), "uploads").toUri().toString();

        // Mapeia a URL "/uploads/**" para a pasta física "uploads/"
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadDir);
    }
}
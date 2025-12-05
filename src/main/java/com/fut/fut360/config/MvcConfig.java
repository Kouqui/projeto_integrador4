//Criado pelo autor: Kauã Kouqui
package com.fut.fut360.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Define a pasta "uploads" na raiz do projeto
        String uploadPath = Paths.get("uploads").toAbsolutePath().toUri().toString();

        // Mapeia a URL /uploads/** para a pasta física
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations(uploadPath);
    }
}
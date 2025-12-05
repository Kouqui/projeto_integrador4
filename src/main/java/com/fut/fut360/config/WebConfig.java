//CRIAÇÃO PELO AUTOR: KAUÊ FARIAS LOURENÇO RA:24788788
//é o interceptador para cotabilizar as paginas ele que da a conexão para ele conseguir conectar

package com.fut.fut360.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AcessoInterceptor acessoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(acessoInterceptor);
    }
}
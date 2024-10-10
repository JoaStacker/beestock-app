package com.api.crud.config;

import com.api.crud.services.models.validation.UserValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//esta clase es necesaria al establecer en models la carpeta valdiation y consecuentemente una validacion de usuario
//es necesaria esta clase puesto que estamos creando un objeto y dicha creacion y manejo es llevada a cabo por un @bean
@Configuration
public class ValidationConfig {
    @Bean
    public UserValidation userValidation(){
        return new UserValidation();
    }
}

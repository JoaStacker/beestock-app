package com.api.crud.config;

import com.api.crud.services.models.validation.UserValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    @Bean
    public UserValidation userValidation(){
        return new UserValidation();
    }
}

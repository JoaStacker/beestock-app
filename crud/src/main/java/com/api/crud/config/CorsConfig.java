package com.api.crud.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
Los CORS permiten que los servidores web controlen el acceso a sus recursos desde otros dominios. Esto es útil en
situaciones donde se necesita permitir que aplicaciones web de un dominio accedan a recursos de otro dominio
 */
public class CorsConfig implements WebMvcConfigurer {
//acordarse que despues se especifican las rutas segun el dominio en la parte de despliegue
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Mapeo general para todas las rutas
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Permitir solicitudes desde este origen. 3000 es para react. ES LA URL DEL FRONTEND
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization") // Encabezados permitidos
                .allowCredentials(true) // Permitir el envío de credenciales (cookies, autenticación HTTP) RUTA PRIVADA
                .maxAge(3600); // Tiempo en segundos que el navegador puede almacenar la configuración CORS

        // Mapeo específico para la ruta de autenticación
        registry.addMapping("/auth/**")
                .allowedOrigins("*") // Permitir solicitudes desde cualquier origen
                .allowedMethods("OPTIONS", "POST") // Solo permitir métodos OPTIONS y POST
                .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization") // Encabezados permitidos
                .allowCredentials(false) // No permitir el envío de credenciales RUTA PUBLICA
                .maxAge(3600); // Tiempo en segundos que el navegador puede almacenar la configuración CORS
    }

}

package com.api.crud.services.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields during serialization
public class ClienteDTO {
    private Optional<String> cuit = Optional.empty();
    private Optional<String> nombre = Optional.empty();
    private Optional<String> apellido = Optional.empty();
    private Optional<String> email = Optional.empty();
    private LocalDateTime fechaNacimiento;

    public Optional<String> getCuit() {
        return cuit;
    }

    public void setCuit(Optional<String> cuit) {
        this.cuit = cuit;
    }

    public Optional<String> getNombre() {
        return nombre;
    }

    public void setNombre(Optional<String> nombre) {
        this.nombre = nombre;
    }

    public Optional<String> getApellido() {
        return apellido;
    }

    public void setApellido(Optional<String> apellido) {
        this.apellido = apellido;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public void setEmail(Optional<String> email) {
        this.email = email;
    }

    public LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}

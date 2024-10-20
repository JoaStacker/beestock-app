package com.api.crud.services.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields during serialization
public class EmpleadoDTO {
    private Optional<String> dni = Optional.empty();
    private Optional<String> nombre = Optional.empty();
    private Optional<String> apellido = Optional.empty();

    public Optional<String> getDni() {
        return dni;
    }

    public void setDni(Optional<String> dni) {
        this.dni = dni;
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
}

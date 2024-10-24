package com.api.crud.services.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields during serialization
public class EmpleadoDTO {
    @NotNull
    @JsonProperty(required = true)
    private String dni;

    @NotNull
    @JsonProperty(required = true)
    private String nombre;

    @NotNull
    @JsonProperty(required = true)
    private String apellido;

    public EmpleadoDTO() {
    }

    public EmpleadoDTO(String dni, String nombre, String apellido) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public @NotNull String getDni() {
        return dni;
    }

    public void setDni(@NotNull String dni) {
        this.dni = dni;
    }

    public @NotNull String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull String nombre) {
        this.nombre = nombre;
    }

    public @NotNull String getApellido() {
        return apellido;
    }

    public void setApellido(@NotNull String apellido) {
        this.apellido = apellido;
    }
}

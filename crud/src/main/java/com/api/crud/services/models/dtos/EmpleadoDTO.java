package com.api.crud.services.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
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

    @NotNull
    @JsonProperty(required = true)
    private String email;

    @NotNull
    @JsonProperty(required = true)
    private String numero;

    @NotNull
    @JsonProperty(required = true)
    private String calle;

    @NotNull
    @JsonProperty(required = true)
    private String piso;

    @NotNull
    @JsonProperty(required = true)
    private Long localidadId;

    @NotNull
    @JsonProperty(required = true)
    private Long puestoId;

    @NotNull
    @JsonProperty(required = true)
    private LocalDateTime fechaIngreso;

    @JsonProperty(required = false)
    private Boolean borrado;

    public EmpleadoDTO() {
    }

    public EmpleadoDTO(String dni, String nombre, String apellido, String email, String numero, String calle, String piso, Long localidadId) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.numero = numero;
        this.calle = calle;
        this.piso = piso;
        this.localidadId = localidadId;
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

    public @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public @NotNull String getNumero() {
        return numero;
    }

    public void setNumero(@NotNull String numero) {
        this.numero = numero;
    }

    public @NotNull String getCalle() {
        return calle;
    }

    public void setCalle(@NotNull String calle) {
        this.calle = calle;
    }

    public @NotNull String getPiso() {
        return piso;
    }

    public void setPiso(@NotNull String piso) {
        this.piso = piso;
    }

    public @NotNull Long getLocalidadId() {
        return localidadId;
    }

    public void setLocalidadId(@NotNull Long localidadId) {
        this.localidadId = localidadId;
    }

    public @NotNull Long getPuestoId() {
        return puestoId;
    }

    public void setPuestoId(@NotNull Long puestoId) {
        this.puestoId = puestoId;
    }

    public @NotNull LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(@NotNull LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }
}

package com.api.crud.services.models.dtos;

import com.api.crud.persistence.entities.CondicionTributaria;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL) // Excluir campos nulos durante la serialización
public class ClienteDTO {

    @NotNull
    @JsonProperty(required = true)
    private String cuit;

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
    private LocalDateTime fechaNacimiento;

    private Boolean borrado;

    @NotNull
    @JsonProperty(required = true)
    private Long condicionTributariaId;

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


    public ClienteDTO() {
    }

    public ClienteDTO(String cuit, String nombre, String apellido, String email, LocalDateTime fechaNacimiento, Long condicionTributariaId, String calle, String numero, String piso, Long localidadId) {
        this.cuit = cuit;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.condicionTributariaId = condicionTributariaId;
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.localidadId = localidadId;
    }

    // Getters y Setters

    public @NotNull String getCuit() {
        return cuit;
    }

    public void setCuit(@NotNull String cuit) {
        this.cuit = cuit;
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

    public @NotNull LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(@NotNull LocalDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }

    public @NotNull Long getCondicionTributariaId() {
        return condicionTributariaId;
    }

    public void setCondicionTributariaId(@NotNull Long condicionTributariaId) {
        this.condicionTributariaId = condicionTributariaId;
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

}

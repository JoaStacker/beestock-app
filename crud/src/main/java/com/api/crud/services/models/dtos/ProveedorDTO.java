package com.api.crud.services.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // Excluir campos nulos durante la serialización
public class ProveedorDTO {

    @NotNull
    @JsonProperty(required = true)
    private String nombre;

    @NotNull
    @JsonProperty(required = true)
    private String cuit;

    @NotNull
    @JsonProperty(required = true)
    private String correo;

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
    private List<Long> tipoServicios;

    @JsonProperty(required = false)
    private Boolean borrado;

    public ProveedorDTO() {
    }

    public ProveedorDTO(String nombre, String cuit, String correo, String numero, String calle, String piso, Long localidadId, List<Long> tipoServicios) {
        this.nombre = nombre;
        this.cuit = cuit;
        this.correo = correo;
        this.numero = numero;
        this.calle = calle;
        this.piso = piso;
        this.localidadId = localidadId;
        this.tipoServicios = tipoServicios;
    }


    // Getters y Setters

    public @NotNull String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull String nombre) {
        this.nombre = nombre;
    }

    public @NotNull String getCuit() {
        return cuit;
    }

    public void setCuit(@NotNull String cuit) {
        this.cuit = cuit;
    }

    public @NotNull String getCorreo() {
        return correo;
    }

    public void setCorreo(@NotNull String correo) {
        this.correo = correo;
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

    public @NotNull List<Long> getTipoServicios() {
        return tipoServicios;
    }

    public void setTipoServicios(@NotNull List<Long> tipoServicios) {
        this.tipoServicios = tipoServicios;
    }

    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }
}

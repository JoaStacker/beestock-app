package com.api.crud.services.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL) // Excluir campos nulos durante la serialización
public class IncidenteDTO {

    @NotNull
    @JsonProperty(required = true)
    private String descripcion;

    @NotNull
    @JsonProperty(required = true)
    private LocalDateTime fechaIncidente;

    @NotNull
    private LocalDateTime fechaSolucion;

    @NotNull
    @JsonProperty(required = true)
    private Long proveedorId;

    @NotNull
    @JsonProperty(required = true)
    private Long estado;

    public IncidenteDTO() {}

    public IncidenteDTO(String descripcion, LocalDateTime fechaIncidente, LocalDateTime fechaSolucion, Long proveedorId, Long estado) {
        this.descripcion = descripcion;
        this.fechaIncidente = fechaIncidente;
        this.fechaSolucion = fechaSolucion;
        this.proveedorId = proveedorId;
        this.estado = estado;
    }

    public @NotNull String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NotNull String descripcion) {
        this.descripcion = descripcion;
    }

    public @NotNull LocalDateTime getFechaIncidente() {
        return fechaIncidente;
    }

    public void setFechaIncidente(@NotNull LocalDateTime fechaIncidente) {
        this.fechaIncidente = fechaIncidente;
    }

    public @NotNull LocalDateTime getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(@NotNull LocalDateTime fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public @NotNull Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(@NotNull Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public @NotNull Long getEstado() {
        return estado;
    }

    public void setEstado(@NotNull Long estado) {
        this.estado = estado;
    }
}

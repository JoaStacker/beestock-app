package com.api.crud.services.models.response.incidentes;

import java.time.LocalDateTime;

public class IncidenteResponseDTO {
    private Long id;
    private String descripcion;
    private LocalDateTime fechaIncidente;
    private LocalDateTime fechaSolucion;

    public IncidenteResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaIncidente() {
        return fechaIncidente;
    }

    public void setFechaIncidente(LocalDateTime fechaIncidente) {
        this.fechaIncidente = fechaIncidente;
    }

    public LocalDateTime getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(LocalDateTime fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }
}

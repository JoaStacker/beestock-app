package com.api.crud.services.models.response.incidentes;

import java.time.LocalDateTime;

public class IncidenteResponseDTO {
    private Long id;
    private String descripcion;
    private LocalDateTime fechaIncidente;
    private LocalDateTime fechaSolucion;
    private Long proveedorId;
    private String nombreProveedor;
    private Long estadoId;
    private String estado;

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

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public Long getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(Long estadoId) {
        this.estadoId = estadoId;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }
}

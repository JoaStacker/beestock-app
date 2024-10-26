package com.api.crud.services.models.response.puesto;

import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;

import java.time.LocalDateTime;

public class PuestoResponseDTO {
    private Long id;
    private String nombre;

    public PuestoResponseDTO() {
    }

    public PuestoResponseDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}

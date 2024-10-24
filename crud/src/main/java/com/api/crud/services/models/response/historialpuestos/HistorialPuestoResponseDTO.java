package com.api.crud.services.models.response.historialpuestos;

import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.HistorialPuesto;
import com.api.crud.persistence.entities.Puesto;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;
import com.api.crud.services.models.response.puesto.PuestoResponseDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class HistorialPuestoResponseDTO {
    private Long id;
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private EmpleadoResponseDTO empleado;
    private PuestoResponseDTO puesto;

    public HistorialPuestoResponseDTO() {
    }

    public HistorialPuestoResponseDTO(Long id, LocalDateTime fechaIngreso, LocalDateTime fechaSalida, EmpleadoResponseDTO empleado, PuestoResponseDTO puesto) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.empleado = empleado;
        this.puesto = puesto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public EmpleadoResponseDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoResponseDTO empleado) {
        this.empleado = empleado;
    }

    public PuestoResponseDTO getPuesto() {
        return puesto;
    }

    public void setPuesto(PuestoResponseDTO puesto) {
        this.puesto = puesto;
    }
}

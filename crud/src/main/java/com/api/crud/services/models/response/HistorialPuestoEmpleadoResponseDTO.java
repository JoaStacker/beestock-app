package com.api.crud.services.models.response;

import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.HistorialPuesto;

import java.util.List;

public class HistorialPuestoEmpleadoResponseDTO {
    private Long empleadoId;
    private List<HistorialPuesto> historialPuestos;

    public HistorialPuestoEmpleadoResponseDTO() {
    }

    public Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public List<HistorialPuesto> getHistorialPuestos() {
        return historialPuestos;
    }

    public void setHistorialPuestos(List<HistorialPuesto> historialPuestos) {
        this.historialPuestos = historialPuestos;
    }
}

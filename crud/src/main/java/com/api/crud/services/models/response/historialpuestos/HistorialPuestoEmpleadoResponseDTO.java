package com.api.crud.services.models.response.historialpuestos;

import java.util.List;

public class HistorialPuestoEmpleadoResponseDTO {
    private String puestoNombre;
    private Long puestoId;

    private List<HistorialPuestoResponseDTO> historialPuestos;

    public HistorialPuestoEmpleadoResponseDTO() {
    }

    public List<HistorialPuestoResponseDTO> getHistorialPuestos() {
        return historialPuestos;
    }

    public void setHistorialPuestos(List<HistorialPuestoResponseDTO> historialPuestos) {
        this.historialPuestos = historialPuestos;
    }

    public String getPuestoNombre() {
        return puestoNombre;
    }

    public void setPuestoNombre(String puestoNombre) {
        this.puestoNombre = puestoNombre;
    }

    public Long getPuestoId() {
        return puestoId;
    }

    public void setPuestoId(Long puestoId) {
        this.puestoId = puestoId;
    }
}

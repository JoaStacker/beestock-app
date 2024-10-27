package com.api.crud.services.models.response.incidentes;

import java.util.List;

public class IncidentesResponseDTO {
    private List<IncidenteResponseDTO> incidentes;

    public IncidentesResponseDTO() {
    }

    public List<IncidenteResponseDTO> getIncidentes() {
        return incidentes;
    }

    public void setIncidentes(List<IncidenteResponseDTO> puestos) {
        this.incidentes = puestos;
    }
}

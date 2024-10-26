package com.api.crud.services.models.response.puesto;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;

import java.util.List;

public class PuestosResponseDTO {
    private List<PuestoResponseDTO> puestos;

    public PuestosResponseDTO() {
    }

    public List<PuestoResponseDTO> getPuestos() {
        return puestos;
    }

    public void setPuestos(List<PuestoResponseDTO> puestos) {
        this.puestos = puestos;
    }
}

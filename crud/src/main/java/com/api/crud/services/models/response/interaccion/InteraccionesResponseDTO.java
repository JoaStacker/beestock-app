package com.api.crud.services.models.response.interaccion;

import com.api.crud.services.models.response.interaccion.InteraccionResponseDTO;

import java.util.List;

public class InteraccionesResponseDTO {

    private List<InteraccionResponseDTO> interacciones;

    public InteraccionesResponseDTO() {}

    public List<InteraccionResponseDTO> getInteracciones() {
        return interacciones;
    }

    public void setInteracciones(List<InteraccionResponseDTO> interacciones) {
        this.interacciones = interacciones;
    }
}

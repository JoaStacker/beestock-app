package com.api.crud.services.models.response.tipoServicio;

import java.util.List;

public class TiposServicioResponseDTO {
    private List<TipoServicioResponseDTO> tiposServicio;

    public TiposServicioResponseDTO() {
    }

    public List<TipoServicioResponseDTO> getTiposServicio() {
        return tiposServicio;
    }

    public void setTiposServicio(List<TipoServicioResponseDTO> puestos) {
        this.tiposServicio = puestos;
    }
}

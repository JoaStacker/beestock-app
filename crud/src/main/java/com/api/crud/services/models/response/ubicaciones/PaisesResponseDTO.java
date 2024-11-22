package com.api.crud.services.models.response.ubicaciones;

import java.util.List;

public class PaisesResponseDTO {
    private List<PaisResponseDTO> paises;

    public List<PaisResponseDTO> getPaises() {
        return paises;
    }

    public void setPaises(List<PaisResponseDTO> paises) {
        this.paises = paises;
    }
}

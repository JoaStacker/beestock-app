package com.api.crud.services.models.response.ubicaciones;

import java.util.List;

public class LocalidadesResponseDTO {
    private List<LocalidadResponseDTO> localidades;

    public List<LocalidadResponseDTO> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<LocalidadResponseDTO> localidades) {
        this.localidades = localidades;
    }
}

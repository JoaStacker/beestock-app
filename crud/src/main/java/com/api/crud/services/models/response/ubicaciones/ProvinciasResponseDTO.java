package com.api.crud.services.models.response.ubicaciones;

import java.util.List;

public class ProvinciasResponseDTO {
    private List<ProvinciaResponseDTO> provincias;

    public List<ProvinciaResponseDTO> getProvincias() {
        return provincias;
    }

    public void setProvincias(List<ProvinciaResponseDTO> provincias) {
        this.provincias = provincias;
    }
}

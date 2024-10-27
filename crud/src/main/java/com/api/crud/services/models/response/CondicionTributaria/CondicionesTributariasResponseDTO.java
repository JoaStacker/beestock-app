package com.api.crud.services.models.response.CondicionTributaria;

import java.util.List;

public class CondicionesTributariasResponseDTO {
    private List<CondicionTributariaResponseDTO> condicionesTributarias;

    public List<CondicionTributariaResponseDTO> getCondicionesTributarias() {
        return condicionesTributarias;
    }

    public void setCondicionesTributarias(List<CondicionTributariaResponseDTO> condicionesTributarias) {
        this.condicionesTributarias = condicionesTributarias;
    }
}

package com.api.crud.services.models.response.ventas;

import java.util.List;

public class VentasResponseDTO {
    private List<VentaResponseDTO> ventas;

    public VentasResponseDTO() {
    }

    public List<VentaResponseDTO> getVentas() {
        return ventas;
    }

    public void setVentas(List<VentaResponseDTO> ventas) {
        this.ventas = ventas;
    }
}

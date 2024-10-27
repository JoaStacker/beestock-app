package com.api.crud.services.models.response.proveedor;

import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;

import java.util.List;

public class ProveedoresResponseDTO {
    private List<ProveedorResponseDTO> proveedores;

    public ProveedoresResponseDTO() {
    }

    public ProveedoresResponseDTO(List<ProveedorResponseDTO> proveedores) {
        this.proveedores = proveedores;
    }

    public List<ProveedorResponseDTO> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<ProveedorResponseDTO> proveedores) {
        this.proveedores = proveedores;
    }
}

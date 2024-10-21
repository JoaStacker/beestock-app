package com.api.crud.services.models.response;

import com.api.crud.persistence.entities.Cliente;

import java.util.List;

public class ClientesResponseDTO {
    private List<Cliente> clientes;

    public ClientesResponseDTO() {
    }

    public ClientesResponseDTO(List<Cliente> cls) {
        this.clientes = cls;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

}

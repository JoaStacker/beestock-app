package com.api.crud.services.models.response;

import com.api.crud.persistence.entities.Cliente;
import com.api.crud.services.models.response.Cliente.ClienteResponseDTO;

import java.util.List;

public class ClientesResponseDTO {
    private List<ClienteResponseDTO> clientes;

    public ClientesResponseDTO() {
    }

    public ClientesResponseDTO(List<ClienteResponseDTO> cls) {
        this.clientes = cls;
    }


    public List<ClienteResponseDTO> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteResponseDTO> clientes) {
        this.clientes = clientes;
    }

}

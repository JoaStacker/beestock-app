package com.api.crud.services;

import com.api.crud.persistence.entities.Cliente;
import com.api.crud.services.models.dtos.ClienteDTO;
import org.springframework.http.ResponseEntity;

public interface IClienteService {

    public ResponseEntity<Object> createCliente(Cliente cliente) throws Exception;
    public ResponseEntity<Object> findAll() throws Exception;
    public ResponseEntity<Object> findOne(Long id) throws Exception;
    public ResponseEntity<Object> updateCliente(Long id, ClienteDTO cliente) throws Exception;
}

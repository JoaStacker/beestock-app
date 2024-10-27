package com.api.crud.services;

import com.api.crud.persistence.entities.Cliente;
import com.api.crud.services.models.dtos.ClienteDTO;
import com.api.crud.services.models.dtos.ProveedorDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface IClienteService {

    public ResponseEntity<Object> createCliente(ClienteDTO clienteDTO) throws Exception;
    public ResponseEntity<Object> findAll() throws Exception;
    public ResponseEntity<Object> findOne(Long id) throws Exception;
    public ResponseEntity<Object> updateCliente(Long id, ClienteDTO cliente) throws Exception;
    public ResponseEntity<Object> deleteCliente(Long id) throws Exception;
    public ResponseEntity<Object> findByMesNacimiento(String mes) throws Exception;
    public ResponseEntity<Object> findAllCondicionesTributarias() throws Exception;
}

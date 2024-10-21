package com.api.crud.services.impl;


import com.api.crud.persistence.entities.Cliente;
import com.api.crud.persistence.repositories.IClienteRepository;
import com.api.crud.services.IClienteService;
import com.api.crud.services.models.dtos.ClienteDTO;
import com.api.crud.services.models.response.ClientesResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteRepository clienteRepository;

    public ResponseEntity<Object> createCliente(Cliente cliente) throws Exception{
        try{
            // Check if the required fields are present
            if (cliente.getCuit() == null || cliente.getCuit().isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'cuit'");
            }
            if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'nombre'");
            }
            if (cliente.getApellido() == null || cliente.getApellido().isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'apellido'");
            }
            if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'email'");
            }
//            if (cliente.getFechaNacimiento() == null) {
//                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'fecha de nacimiento'");
//            }

            // Check if the CUIT already exists
            Optional<Cliente> cli = clienteRepository.findByCuit(cliente.getCuit());
            if (cli.isPresent()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "CUIT ya existe!");
            }

            // Save the new employee
            clienteRepository.save(cliente);
            return ResponseHandler.responseBuilder(HttpStatus.CREATED, "Cliente creado con éxito!", cliente);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear cliente: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> findAll() {
        try {
            List<Cliente> allClientes = clienteRepository.findAll();

            if (allClientes.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "No hay clientes disponibles");
            }

            ClientesResponseDTO response = new ClientesResponseDTO();
            response.setClientes(allClientes);
            return ResponseHandler.responseBuilder(HttpStatus.OK, "Clientes encontrados con exito", response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener clientes: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> findOne(Long id) throws Exception {
        try{
            Optional<Cliente> cliente = clienteRepository.findById(id);
            if(cliente.isPresent()){
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Cliente encontrado con exito", cliente);
            }else{
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "Cliente no existe", cliente);
            }
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }

    public ResponseEntity<Object> updateCliente(Long id, ClienteDTO clienteDetails) throws Exception {
        try{
            Optional<Cliente> optionalUser = clienteRepository.findById(id);

            if (optionalUser.isPresent()) {
                Cliente clienteToUpdate = getClienteToUpdate(clienteDetails, optionalUser);
                clienteRepository.save(clienteToUpdate);
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Cliente actualizado con exito", clienteToUpdate);
            } else {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Cliente no existe");
            }
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }

    private static Cliente getClienteToUpdate(ClienteDTO clienteDetails, Optional<Cliente> optionalUser) {
        Cliente clienteToUpdate = optionalUser.get();
        if(clienteDetails.getCuit().isPresent()){
            clienteToUpdate.setCuit(clienteDetails.getCuit().get());
        }
        if(clienteDetails.getNombre().isPresent()){
            clienteToUpdate.setNombre(clienteDetails.getNombre().get());
        }
        if(clienteDetails.getApellido().isPresent()){
            clienteToUpdate.setApellido(clienteDetails.getApellido().get());
        }
        if(clienteDetails.getEmail().isPresent()){
            clienteToUpdate.setEmail(clienteDetails.getEmail().get());
        }
        return clienteToUpdate;
    }
}

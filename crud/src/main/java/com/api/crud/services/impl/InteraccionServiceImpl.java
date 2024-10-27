package com.api.crud.services.impl;

import com.api.crud.services.models.response.ClientesResponseDTO;
import com.api.crud.services.models.response.Cliente.ClienteResponseDTO;
import com.api.crud.persistence.entities.Cliente;
import com.api.crud.persistence.entities.Interaccion;
import com.api.crud.persistence.repositories.IInteraccionRepository;
import com.api.crud.services.models.IInteraccionService;
import com.api.crud.services.models.response.ResponseHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

@Service
public class InteraccionServiceImpl implements IInteraccionService {

    @Autowired
    private IInteraccionRepository interaccionRepository;
    @Autowired
    private HttpServletResponse response;


    public ResponseEntity<Object> gettById(Integer id) throws Exception {
        return null;
    }

    public ResponseEntity<Object> findOne(Long id) throws Exception {
        try {
            Optional<Cliente> interaccionFound = interaccionRepository.findById(id);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Cliente encontrado con exito", response);
        } catch (Exception e) {
            throw new Exception(e.toString());
        }

    }



}
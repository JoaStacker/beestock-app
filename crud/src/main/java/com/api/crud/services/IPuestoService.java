package com.api.crud.services;

import com.api.crud.services.models.dtos.HistorialPuestoDTO;
import org.springframework.http.ResponseEntity;

public interface IPuestoService {
    public ResponseEntity<Object> findAll() throws Exception;
}

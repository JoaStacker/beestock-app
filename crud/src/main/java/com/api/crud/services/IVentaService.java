package com.api.crud.services;

import com.api.crud.services.models.dtos.VentaDTO;
import org.springframework.http.ResponseEntity;

public interface IVentaService {
    public ResponseEntity<Object> getAll() throws Exception;
    public ResponseEntity<Object> createVenta(VentaDTO body) throws Exception;
}

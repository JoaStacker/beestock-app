package com.api.crud.services;

import com.api.crud.services.models.dtos.VentaDTO;
import org.springframework.http.ResponseEntity;

public interface IVentaService {
    public ResponseEntity<Object> getAll() throws Exception;
    public ResponseEntity<Object> createVenta(VentaDTO body) throws Exception;
    public ResponseEntity<Object> getAllByCliente(Long id) throws Exception;
    public ResponseEntity<Object> findOne(Long id) throws Exception;
}

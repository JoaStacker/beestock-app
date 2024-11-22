package com.api.crud.services;

import com.api.crud.services.models.dtos.InteraccionDTO;
import org.springframework.http.ResponseEntity;

public interface IInteraccionService {
    public ResponseEntity<Object> create(InteraccionDTO interaccion) throws Exception;
    public ResponseEntity<Object> findOne(Long id) throws Exception;
    public ResponseEntity<Object> getAllByCliente(Long id) throws Exception;
    public ResponseEntity<Object> updateOne(Long id, InteraccionDTO interaccion) throws Exception;
}

package com.api.crud.services.models;

import com.api.crud.persistence.entities.Interaccion;
import org.springframework.http.ResponseEntity;

public interface IInteraccionService {


    public ResponseEntity<Object> findOne(Long id) throws Exception;


}

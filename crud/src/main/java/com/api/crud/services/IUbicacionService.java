package com.api.crud.services;

import com.api.crud.services.models.dtos.VentaDTO;
import org.springframework.http.ResponseEntity;

public interface IUbicacionService {
    public ResponseEntity<Object> getAllPaises() throws Exception;
    public ResponseEntity<Object> getAllProvinciasByPais(Long id) throws Exception;
    public ResponseEntity<Object> getAllLocalidadesByProvincia(Long id) throws Exception;
}

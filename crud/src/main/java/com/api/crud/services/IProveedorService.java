package com.api.crud.services;

import com.api.crud.persistence.entities.Proveedor;
import com.api.crud.services.models.dtos.ProveedorDTO;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IProveedorService {
    public ResponseEntity<Object> createProveedor(ProveedorDTO proveedorDTO) throws Exception;
}
package com.api.crud.services;

import com.api.crud.persistence.entities.Proveedor;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.dtos.ProveedorDTO;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface IProveedorService {
    public ResponseEntity<Object> createProveedor(ProveedorDTO proveedorDTO) throws Exception;
    public ResponseEntity<Object> deleteProveedorById(Long id) throws Exception;
    //public ResponseEntity<Object> updateProveedor(ProveedorDTO proveedorDTO) throws Exception;
    public ResponseEntity<Object> findAll() throws Exception;
    public ResponseEntity<Object> findOne(Long id) throws Exception;
    public ResponseEntity<Object> updateProveedor(Long id,  ProveedorDTO proveedorDTO) throws Exception;
    public ResponseEntity<Object> findAllTiposServicios() throws Exception;
    public ResponseEntity<Object> getRankingIncidentes() throws Exception;

}

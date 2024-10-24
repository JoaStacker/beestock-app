package com.api.crud.services;

import com.api.crud.services.models.dtos.EmpleadoDTO;
import org.springframework.http.ResponseEntity;

public interface IEmpleadoService {

    public ResponseEntity<Object> createEmpleado(EmpleadoDTO empleado) throws Exception;
    public ResponseEntity<Object> findAll() throws Exception;
    public ResponseEntity<Object> findOne(Long id) throws Exception;
    public ResponseEntity<Object> updateEmpleado(Long id, EmpleadoDTO empleado) throws Exception;
}

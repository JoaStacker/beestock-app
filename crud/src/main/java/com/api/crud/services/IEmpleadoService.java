package com.api.crud.services;

import com.api.crud.persistence.entities.Empleado;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.response.EmpleadosResponseDTO;
import com.api.crud.services.models.response.ResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IEmpleadoService {

    public ResponseEntity<Object> createEmpleado(Empleado empleado) throws Exception;
    public ResponseEntity<Object> findAll() throws Exception;
    public ResponseEntity<Object> findOne(Long id) throws Exception;
    public ResponseEntity<Object> updateEmpleado(Long id, EmpleadoDTO empleado) throws Exception;
}

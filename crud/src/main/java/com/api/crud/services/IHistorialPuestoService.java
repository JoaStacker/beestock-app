package com.api.crud.services;

import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.HistorialPuesto;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.dtos.HistorialPuestoDTO;
import org.springframework.http.ResponseEntity;

public interface IHistorialPuestoService {
    public ResponseEntity<Object> create(HistorialPuestoDTO body) throws Exception;
    public ResponseEntity<Object> getAllByEmpleado(Long id) throws Exception;
}

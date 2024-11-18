package com.api.crud.services;

import com.api.crud.services.models.dtos.IncidenteDTO;
import com.api.crud.services.models.dtos.InteraccionDTO;
import com.api.crud.services.models.response.incidentes.IncidenteResponseDTO;
import org.springframework.http.ResponseEntity;

public interface IIncidenteService {
    public ResponseEntity<Object> createOne(IncidenteDTO body) throws Exception;
    public ResponseEntity<Object> findAll() throws Exception;
    public ResponseEntity<Object> getOne(Long id) throws Exception;
    public ResponseEntity<Object> updateOne(Long id, IncidenteDTO body) throws Exception;
}

package com.api.crud.services.impl;

import com.api.crud.persistence.entities.*;
import com.api.crud.persistence.repositories.*;
import com.api.crud.services.IIncidenteService;
import com.api.crud.services.IInteraccionService;
import com.api.crud.services.models.dtos.IncidenteDTO;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.incidentes.IncidenteResponseDTO;
import com.api.crud.services.models.response.incidentes.IncidentesResponseDTO;
import com.api.crud.services.models.response.interaccion.InteraccionResponseDTO;
import com.api.crud.services.models.response.proveedor.ProveedorResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IncidenteServiceImpl implements IIncidenteService {

    @Autowired
    private IIncidenteRepository incidenteRepository;

    @Autowired
    private IProveedorRepository proveedorRepository;

    public ResponseEntity<Object> createOne(IncidenteDTO body) throws Exception {
        try {
            Optional<Proveedor> proveedor = proveedorRepository.findById(body.getProveedorId());
            if (proveedor.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "El proveedor no existe.");
            }
            Incidente nuevoIncidente = new Incidente(
                    body.getDescripcion(),
                    body.getFechaIncidente(),
                    body.getFechaSolucion(),
                    proveedor.get()
            );
            incidenteRepository.save(nuevoIncidente);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Incidente creado con exito.");
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "internal Server Error.");
        }
    }

    public ResponseEntity<Object> findAll() throws Exception {
        try{
            List<Incidente> allIncidentes = incidenteRepository.findAll();
            if (allIncidentes.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "No hay incidentes disponibles");
            }

            IncidentesResponseDTO response = new IncidentesResponseDTO();
            List<IncidenteResponseDTO> incidentesList = new ArrayList<>();
            for(Incidente incidente: allIncidentes){
                IncidenteResponseDTO inc = new IncidenteResponseDTO();
                inc.setId(incidente.getId());
                inc.setFechaIncidente(incidente.getFechaIncidente());
                inc.setFechaSolucion(incidente.getFechaSolucion());
                inc.setDescripcion(incidente.getDescripcion());
                incidentesList.add(inc);
            }
            response.setIncidentes(incidentesList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Incidentes devueltos con exito", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al devolver Incidentes: " + e.getMessage());
        }
    }

}
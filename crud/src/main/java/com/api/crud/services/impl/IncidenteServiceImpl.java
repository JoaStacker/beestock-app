package com.api.crud.services.impl;

import com.api.crud.persistence.entities.*;
import com.api.crud.persistence.repositories.*;
import com.api.crud.services.IIncidenteService;
import com.api.crud.services.IInteraccionService;
import com.api.crud.services.models.dtos.IncidenteDTO;
import com.api.crud.services.models.response.Cliente.ClienteResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.direccion.LocalidadResponseDTO;
import com.api.crud.services.models.response.direccion.PaisResponseDTO;
import com.api.crud.services.models.response.direccion.ProvinciaResponseDTO;
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
                    proveedor.get(),
                    body.getEstado()
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
                if(incidente.getEstado() != null){
                    String estado = incidente.getEstado() == 1 ? "PENDIENTE" : "SOLUCIONADO";
                    inc.setEstado(estado);
                }
                inc.setNombreProveedor(incidente.getProveedor().getNombre());
                incidentesList.add(inc);
            }
            response.setIncidentes(incidentesList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Incidentes devueltos con exito", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al devolver Incidentes: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> getOne(Long incidenteId) throws Exception {
        try {
            Optional<Incidente> incidenteOptional = incidenteRepository.findById(incidenteId);
            if (incidenteOptional.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "El incidente no existe.");
            }
            Incidente incidente = incidenteOptional.get();
            IncidenteResponseDTO response = new IncidenteResponseDTO();
            response.setId(incidente.getId());
            response.setDescripcion(incidente.getDescripcion());
            response.setProveedorId(incidente.getProveedor().getId());
            response.setFechaIncidente(incidente.getFechaIncidente());
            response.setFechaSolucion(incidente.getFechaSolucion());
            response.setEstadoId(incidente.getEstado());

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Incidente devuelto con exito.", response);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "internal Server Error.");
        }
    }

    public ResponseEntity<Object> updateOne(Long incidenteId, IncidenteDTO body) throws Exception {
        try{
            Optional<Incidente> optionalIncidente = incidenteRepository.findById(incidenteId);

            if (optionalIncidente.isPresent()) {
                Incidente incidenteToUpdate = optionalIncidente.get();
                if(body.getDescripcion() != null){
                    incidenteToUpdate.setDescripcion(body.getDescripcion());
                }
                if(body.getFechaIncidente() != null){
                    incidenteToUpdate.setFechaIncidente(body.getFechaIncidente());
                }
                if(body.getFechaSolucion() != null){
                    incidenteToUpdate.setFechaSolucion(body.getFechaSolucion());
                }else{
                    incidenteToUpdate.setFechaSolucion(null);
                }
                if(body.getEstado() != null){
                    incidenteToUpdate.setEstado(body.getEstado());
                }
                if(body.getProveedorId() != null){
                    Optional<Proveedor> optionalProveedor = proveedorRepository.findById(body.getProveedorId());
                    if(optionalProveedor.isEmpty()){
                        return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Proveedor id no existe!");
                    }
                    incidenteToUpdate.setProveedor(optionalProveedor.get());
                }

                incidenteRepository.save(incidenteToUpdate);

                return ResponseHandler.responseBuilder(HttpStatus.OK, "Incidente actualizado con exito");
            } else {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Incidente no existe");
            }
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error.");
        }
    }

}
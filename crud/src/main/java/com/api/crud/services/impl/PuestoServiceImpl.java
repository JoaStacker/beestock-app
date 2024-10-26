package com.api.crud.services.impl;


import com.api.crud.persistence.entities.Direccion;
import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.HistorialPuesto;
import com.api.crud.persistence.entities.Puesto;
import com.api.crud.persistence.repositories.IEmpleadoRepository;
import com.api.crud.persistence.repositories.IHistorialPuestoRepository;
import com.api.crud.persistence.repositories.IPuestoRepository;
import com.api.crud.services.IHistorialPuestoService;
import com.api.crud.services.IPuestoService;
import com.api.crud.services.models.dtos.HistorialPuestoDTO;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;
import com.api.crud.services.models.response.empleado.EmpleadosResponseDTO;
import com.api.crud.services.models.response.historialpuestos.HistorialPuestoEmpleadoResponseDTO;
import com.api.crud.services.models.response.historialpuestos.HistorialPuestoResponseDTO;
import com.api.crud.services.models.response.puesto.PuestoResponseDTO;
import com.api.crud.services.models.response.puesto.PuestosResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PuestoServiceImpl implements IPuestoService {

    @Autowired
    private IPuestoRepository puestoRepository;

    public ResponseEntity<Object> findAll() throws Exception {
        try{
            List<Puesto> allPuestos = puestoRepository.findAll();
            if (allPuestos.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "No hay puestos disponibles");
            }

            PuestosResponseDTO response = new PuestosResponseDTO();
            List<PuestoResponseDTO> puestosList = new ArrayList<>();
            for(Puesto puesto: allPuestos){
                PuestoResponseDTO pue = new PuestoResponseDTO();
                pue.setId(puesto.getId());
                pue.setNombre(puesto.getNombre());
                puestosList.add(pue);
            }
            response.setPuestos(puestosList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Puestos devueltos con exito", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al devolver puestos: " + e.getMessage());
        }
    }
}

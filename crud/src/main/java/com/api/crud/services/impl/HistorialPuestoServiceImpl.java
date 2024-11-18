package com.api.crud.services.impl;


import com.api.crud.persistence.entities.*;
import com.api.crud.persistence.repositories.IEmpleadoRepository;
import com.api.crud.persistence.repositories.IHistorialPuestoRepository;
import com.api.crud.persistence.repositories.IPuestoRepository;
import com.api.crud.services.IHistorialPuestoService;
import com.api.crud.services.models.dtos.HistorialPuestoDTO;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;
import com.api.crud.services.models.response.historialpuestos.HistorialPuestoEmpleadoResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.historialpuestos.HistorialPuestoResponseDTO;
import com.api.crud.services.models.response.proveedor.ProveedorResponseDTO;
import com.api.crud.services.models.response.puesto.PuestoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class HistorialPuestoServiceImpl implements IHistorialPuestoService {

    @Autowired
    private IHistorialPuestoRepository historialPuestoRepository;
    @Autowired
    private IPuestoRepository puestoRepository;
    @Autowired
    private IEmpleadoRepository empleadoRepository;

    public ResponseEntity<Object> create(HistorialPuestoDTO body) throws Exception{
        try{
            Optional<Empleado> emp = empleadoRepository.findById(body.getEmpleadoId());
            if (emp.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "El empleado no existe.");
            }

            Optional<Puesto> pue = puestoRepository.findById(body.getPuestoId());
            if (pue.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "El puesto no existe.");
            }

            HistorialPuesto oldHistorialPuesto = historialPuestoRepository.findLastPuestoEmpleado(body.getEmpleadoId());
            if(oldHistorialPuesto != null){
                oldHistorialPuesto.setFechaSalida(body.getFechaIngreso());
                historialPuestoRepository.save(oldHistorialPuesto);
            }

            HistorialPuesto nuevoHistorialPuesto = new HistorialPuesto();
            nuevoHistorialPuesto.setFechaIngreso(body.getFechaIngreso());
            nuevoHistorialPuesto.setFechaSalida(null);
            nuevoHistorialPuesto.setEmpleado(emp.get());
            nuevoHistorialPuesto.setPuesto(pue.get());

            historialPuestoRepository.save(nuevoHistorialPuesto);

            HistorialPuestoResponseDTO response = new HistorialPuestoResponseDTO();
            response.setId(nuevoHistorialPuesto.getId());
            response.setFechaIngreso(nuevoHistorialPuesto.getFechaIngreso());
            response.setFechaSalida(nuevoHistorialPuesto.getFechaSalida());
            Puesto puesto = nuevoHistorialPuesto.getPuesto();
            response.setPuesto(new PuestoResponseDTO(puesto.getId(), puesto.getNombre()));
            Empleado empleado = nuevoHistorialPuesto.getEmpleado();
            response.setEmpleado(new EmpleadoResponseDTO(empleado.getId(), empleado.getDni(), empleado.getNombre(), empleado.getApellido(), empleado.getEmail()));

            return ResponseHandler.responseBuilder(HttpStatus.CREATED, "Nuevo puesto asignado al empleado.", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear historial de puesto: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> getAllByEmpleado(Long id) throws Exception {
        try{
            Optional<Empleado> emp = empleadoRepository.findById(id);
            if (emp.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "El empleado no existe.");
            }
            List<HistorialPuesto> allHistorial = historialPuestoRepository.findByEmpleadoId(id);
            List<HistorialPuestoResponseDTO> historialList = new ArrayList<>();
            String puestoNombre = null;
            Long puestoId = null;

            for(HistorialPuesto hispue : allHistorial){
                HistorialPuestoResponseDTO historialPuestoResponseDTO = new HistorialPuestoResponseDTO();
                historialPuestoResponseDTO.setPuestoId(hispue.getPuesto().getId());
                historialPuestoResponseDTO.setPuestoNombre(hispue.getPuesto().getNombre());
                historialPuestoResponseDTO.setFechaIngreso(hispue.getFechaIngreso());
                historialPuestoResponseDTO.setFechaSalida(hispue.getFechaSalida());

                // Parse the date strings into LocalDate objects
                LocalDateTime date1 = hispue.getFechaIngreso();
                LocalDateTime date2 = hispue.getFechaSalida();

                if(date2 == null){
                    puestoId = hispue.getPuesto().getId();
                    puestoNombre = hispue.getPuesto().getNombre();
                }
                date2 = Objects.requireNonNullElse(date2, LocalDateTime.now());

                // Calculate the difference in years
                Long antiguedadAnos = ChronoUnit.YEARS.between(date1, date2);
                historialPuestoResponseDTO.setAntiguedad(antiguedadAnos);

                historialList.add(historialPuestoResponseDTO);
            }
            HistorialPuestoEmpleadoResponseDTO response = new HistorialPuestoEmpleadoResponseDTO();
            response.setPuestoId(puestoId);
            response.setPuestoNombre(puestoNombre);
            response.setHistorialPuestos(historialList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Historial de puestos del empleado", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear historial de puesto: " + e.getMessage());
        }
    }
}

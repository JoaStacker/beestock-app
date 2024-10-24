package com.api.crud.services.impl;


import com.api.crud.persistence.entities.Direccion;
import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.HistorialPuesto;
import com.api.crud.persistence.entities.Puesto;
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
import com.api.crud.services.models.response.puesto.PuestoResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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
            response.setEmpleado(new EmpleadoResponseDTO(empleado.getId(), empleado.getDni(), empleado.getNombre(), empleado.getApellido()));

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
            List<HistorialPuesto> historialPuestos = historialPuestoRepository.findByEmpleadoId(id);
            HistorialPuestoEmpleadoResponseDTO response = new HistorialPuestoEmpleadoResponseDTO();
            response.setEmpleadoId(id);
            response.setHistorialPuestos(historialPuestos);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Historial de puestos del empleado", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear historial de puesto: " + e.getMessage());
        }
    }
}

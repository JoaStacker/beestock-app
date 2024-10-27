package com.api.crud.services.impl;

import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.repositories.IClienteRepository;
import com.api.crud.persistence.repositories.IEmpleadoRepository;
import com.api.crud.services.models.dtos.InteraccionDTO;
import com.api.crud.services.models.response.Cliente.ClienteResponseDTO;
import com.api.crud.persistence.entities.Cliente;
import com.api.crud.persistence.entities.Interaccion;
import com.api.crud.persistence.repositories.IInteraccionRepository;
import com.api.crud.services.IInteraccionService;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;
import com.api.crud.services.models.response.interaccion.InteraccionResponseDTO;
import com.api.crud.services.models.response.interaccion.InteraccionesResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

@Service
public class InteraccionServiceImpl implements IInteraccionService {

    @Autowired
    private IInteraccionRepository interaccionRepository;

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Autowired
    private IClienteRepository clienteRepository;


    public ResponseEntity<Object> create(InteraccionDTO body) throws Exception {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(body.getClienteId());
            if (cliente.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "El cliente no existe.");
            }
            Optional<Empleado> empleado = empleadoRepository.findById(body.getEmpleadoId());
            if (empleado.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "El empleado no existe.");
            }
            Interaccion nuevaInteraccion = new Interaccion(
                    body.getFechaInteraccion(),
                    body.getMedio(),
                    cliente.get(),
                    empleado.get()
            );
            interaccionRepository.save(nuevaInteraccion);

            InteraccionResponseDTO response = new InteraccionResponseDTO();
            response.setId(nuevaInteraccion.getId());
            response.setFechaInteraccion(nuevaInteraccion.getFechaInteraccion());
            response.setMedio(nuevaInteraccion.getMedio());

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Interaccion listada con exito.", response);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "internal Server Error.");
        }
    }

    public ResponseEntity<Object> findOne(Long id) throws Exception {
        try {
            InteraccionResponseDTO response = new InteraccionResponseDTO();
            return ResponseHandler.responseBuilder(HttpStatus.OK, "Interaccion listada con exito.", response);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "internal Server Error.");
        }
    }

    public ResponseEntity<Object> getAllByCliente(Long id) throws Exception {
        try {
            List<Interaccion> allInteracciones = interaccionRepository.findByCliente(id);

            InteraccionesResponseDTO response = new InteraccionesResponseDTO();
            List<InteraccionResponseDTO> interaccionesList = new ArrayList<>();
            for(Interaccion interaccion: allInteracciones){
                InteraccionResponseDTO inter = new InteraccionResponseDTO();
                inter.setId(interaccion.getId());
                inter.setFechaInteraccion(interaccion.getFechaInteraccion());
                inter.setMedio(interaccion.getMedio());
                Cliente cliente = interaccion.getCliente();
                ClienteResponseDTO cli = new ClienteResponseDTO();
                cli.setId(cliente.getId());
                cli.setNombre(cliente.getNombre());
                cli.setApellido(cliente.getApellido());
                cli.setCuit(cliente.getCuit());
                inter.setCliente(cli);

                Empleado empleado = interaccion.getEmpleado();
                EmpleadoResponseDTO emp = new EmpleadoResponseDTO();
                emp.setId(empleado.getId());
                emp.setNombre(empleado.getNombre());
                emp.setApellido(empleado.getApellido());
                inter.setEmpleado(emp);

                interaccionesList.add(inter);
            }
            response.setInteracciones(interaccionesList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Interacciones para cliente listadas con exito.", response);
        } catch (Exception e) {
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "internal Server Error.");

        }

    }



}
package com.api.crud.services.impl;


import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.repositories.IEmpleadoRepository;//check
import com.api.crud.services.IEmpleadoService;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;
import com.api.crud.services.models.response.empleado.EmpleadosResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;//check
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    public ResponseEntity<Object> createEmpleado(EmpleadoDTO body) throws Exception{
        try{
            Optional<Empleado> emp = empleadoRepository.findByDni(body.getDni());
            if (emp.isPresent()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "DNI ya existe!");
            }

            Empleado nuevoEmpleado = new Empleado(body.getDni(), body.getNombre(), body.getApellido());
            empleadoRepository.save(nuevoEmpleado);

            EmpleadoResponseDTO response = new EmpleadoResponseDTO();
            response.setId(nuevoEmpleado.getId());
            response.setDni(nuevoEmpleado.getDni());
            response.setNombre(nuevoEmpleado.getNombre());
            response.setApellido(nuevoEmpleado.getApellido());

            return ResponseHandler.responseBuilder(HttpStatus.CREATED, "Empleado creado con éxito!", response);
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear empleado: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> findAll() {
        try {
            List<Empleado> allEmpleados = empleadoRepository.findAll();
            if (allEmpleados.isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "No hay empleados disponibles");
            }

            EmpleadosResponseDTO response = new EmpleadosResponseDTO();
            response.setEmpleados(allEmpleados);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Empleados encontrados con exito", response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener empleados: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> findOne(Long id) throws Exception {
        try{
            Optional<Empleado> empleado = empleadoRepository.findById(id);
            if(empleado.isPresent()){
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Empleados encontrados con exito", empleado);
            }else{
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "Empleado no existe", empleado);
            }
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }

    public ResponseEntity<Object> updateEmpleado(Long id, EmpleadoDTO body) throws Exception {
        try{
            Optional<Empleado> optionalEmpleado = empleadoRepository.findById(id);

            if (optionalEmpleado.isPresent()) {
                Empleado empleado = optionalEmpleado.get();
                Empleado empleadoToUpdate = getEmpleadoToUpdate(body, empleado);
                empleadoRepository.save(empleadoToUpdate);
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Empleado actualizado con exito", empleadoToUpdate);
            } else {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Empleado no existe");
            }
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }

    private static Empleado getEmpleadoToUpdate(EmpleadoDTO body, Empleado empleado) {
        if(!body.getDni().isEmpty()){
            empleado.setDni(body.getDni());
        }
        if(!body.getNombre().isEmpty()){
            empleado.setNombre(body.getNombre());
        }
        if(!body.getApellido().isEmpty()){
            empleado.setApellido(body.getApellido());
        }
        return empleado;
    }
}

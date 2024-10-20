package com.api.crud.services.impl;


import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.persistence.repositories.IEmpleadoRepository;//check
import com.api.crud.services.IEmpleadoService;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.response.EmpleadosResponseDTO;
import com.api.crud.services.models.response.ResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.UserResponseDTO;
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

    public ResponseEntity<Object> createEmpleado(Empleado empleado) throws Exception{
        try{
            // Check if the required fields are present
            if (empleado.getDni() == null || empleado.getDni().isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'dni'");
            }
            if (empleado.getNombre() == null || empleado.getNombre().isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'nombre'");
            }
            if (empleado.getApellido() == null || empleado.getApellido().isEmpty()) {
                return ResponseHandler.responseBuilder(HttpStatus.BAD_REQUEST, "Falta campo 'apellido'");
            }

            // Check if the DNI already exists
            Optional<Empleado> emp = empleadoRepository.findByDni(empleado.getDni());
            if (emp.isPresent()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "DNI ya existe!");
            }

            // Save the new employee
            empleadoRepository.save(empleado);
            return ResponseHandler.responseBuilder(HttpStatus.CREATED, "Empleado creado con éxito!", empleado);
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

    public ResponseEntity<Object> updateEmpleado(Long id, EmpleadoDTO empleadoDetails) throws Exception {
        try{
            Optional<Empleado> optionalUser = empleadoRepository.findById(id);

            if (optionalUser.isPresent()) {
                Empleado empleadoToUpdate = getEmpleadoToUpdate(empleadoDetails, optionalUser);
                empleadoRepository.save(empleadoToUpdate);
                return ResponseHandler.responseBuilder(HttpStatus.OK, "Empleado actualizado con exito", empleadoToUpdate);
            } else {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Empleado no existe");
            }
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }

    private static Empleado getEmpleadoToUpdate(EmpleadoDTO empleadoDetails, Optional<Empleado> optionalUser) {
        Empleado empleadoToUpdate = optionalUser.get();
        if(empleadoDetails.getDni().isPresent()){
            empleadoToUpdate.setDni(empleadoDetails.getDni().get());
        }
        if(empleadoDetails.getNombre().isPresent()){
            empleadoToUpdate.setNombre(empleadoDetails.getNombre().get());
        }
        if(empleadoDetails.getApellido().isPresent()){
            empleadoToUpdate.setApellido(empleadoDetails.getApellido().get());
        }
        return empleadoToUpdate;
    }
}

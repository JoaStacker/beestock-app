package com.api.crud.services.impl;


import com.api.crud.persistence.entities.*;
import com.api.crud.persistence.repositories.IEmpleadoRepository;//check
import com.api.crud.persistence.repositories.ILocalidadRepository;
import com.api.crud.services.IEmpleadoService;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.direccion.LocalidadResponseDTO;
import com.api.crud.services.models.response.direccion.PaisResponseDTO;
import com.api.crud.services.models.response.direccion.ProvinciaResponseDTO;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;
import com.api.crud.services.models.response.empleado.EmpleadosResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;//check
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Autowired
    private ILocalidadRepository localidadRepository;

    public ResponseEntity<Object> createEmpleado(EmpleadoDTO body) throws Exception{
        try{
            Optional<Empleado> emp = empleadoRepository.findByDni(body.getDni());
            if (emp.isPresent()) {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "DNI ya existe!");
            }

            Optional<Localidad> localidad = localidadRepository.findById(body.getLocalidadId());
            if(localidad.isEmpty()){
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Localidad id no existe!", localidad);
            }

            Empleado nuevoEmpleado = new Empleado(body.getNombre(), body.getApellido(), body.getDni(), body.getCalle(), body.getNumero(), body.getPiso(), localidad.get());
            empleadoRepository.save(nuevoEmpleado);

            EmpleadoResponseDTO response = new EmpleadoResponseDTO();
            response.setId(nuevoEmpleado.getId());
            response.setDni(nuevoEmpleado.getDni());
            response.setNombre(nuevoEmpleado.getNombre());
            response.setApellido(nuevoEmpleado.getApellido());

            Direccion direccion = nuevoEmpleado.getDireccion();
            DireccionResponseDTO dir = new DireccionResponseDTO(
                    direccion.getId(),
                    direccion.getCalle(),
                    direccion.getNumero(),
                    direccion.getPiso()
            );
            response.setDireccion(dir);

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

            List<EmpleadoResponseDTO> empleadoList = new ArrayList<>();
            for(Empleado empleado: allEmpleados){
                EmpleadoResponseDTO emp = new EmpleadoResponseDTO();
                emp.setId(empleado.getId());
                emp.setDni(empleado.getDni());
                emp.setNombre(empleado.getNombre());
                emp.setApellido(empleado.getApellido());
                Direccion direccion = empleado.getDireccion();
                DireccionResponseDTO dir = new DireccionResponseDTO(
                        direccion.getId(),
                        direccion.getCalle(),
                        direccion.getNumero(),
                        direccion.getPiso()
                );
                emp.setDireccion(dir);
                empleadoList.add(emp);
            }
            EmpleadosResponseDTO response = new EmpleadosResponseDTO();
            response.setEmpleados(empleadoList);

            return ResponseHandler.responseBuilder(HttpStatus.OK, "Empleados encontrados con exito", response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener empleados: " + e.getMessage());
        }
    }

    public ResponseEntity<Object> findOne(Long id) throws Exception {
        try{
            Optional<Empleado> empleadoFound = empleadoRepository.findById(id);
            if(empleadoFound.isPresent()){
                Empleado empleado = empleadoFound.get();
                EmpleadoResponseDTO response = new EmpleadoResponseDTO();
                response.setId(empleado.getId());
                response.setDni(empleado.getDni());
                response.setNombre(empleado.getNombre());
                response.setApellido(empleado.getApellido());
                Direccion direccion = empleado.getDireccion();
                Localidad localidad = direccion.getLocalidad();
                Provincia provincia = localidad.getProvincia();
                Pais pais = provincia.getPais();

                response.setDireccion(new DireccionResponseDTO(
                        direccion.getId(),
                        direccion.getCalle(),
                        direccion.getNumero(),
                        direccion.getPiso()
                ));
                response.setLocalidad(new LocalidadResponseDTO(
                        localidad.getId(),
                        localidad.getNombreLocalidad()
                ));
                response.setProvincia(new ProvinciaResponseDTO(
                        provincia.getId(),
                        provincia.getNombreProvincia()
                ));
                response.setPais(new PaisResponseDTO(
                        pais.getId(),
                        pais.getNombrePais()
                ));

                return ResponseHandler.responseBuilder(HttpStatus.OK, "Empleado encontrado con exito", response);
            }else{
                return ResponseHandler.responseBuilder(HttpStatus.NO_CONTENT, "Empleado no existe");
            }
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }

    public ResponseEntity<Object> updateEmpleado(Long id, EmpleadoDTO body) throws Exception {
        try{
            Optional<Empleado> optionalEmpleado = empleadoRepository.findById(id);

            if (optionalEmpleado.isPresent()) {
                Empleado empleadoToUpdate = optionalEmpleado.get();
                if(body.getDni() != null){
                    empleadoToUpdate.setDni(body.getDni());
                }
                if(body.getNombre() != null){
                    empleadoToUpdate.setNombre(body.getNombre());
                }
                if(body.getApellido() != null){
                    empleadoToUpdate.setApellido(body.getApellido());
                }
                if(body.getCalle() != null){
                    Direccion direccion = empleadoToUpdate.getDireccion();
                    direccion.setCalle(body.getCalle());
                    empleadoToUpdate.setDireccion(direccion);
                }
                if(body.getNumero() != null){
                    Direccion direccion = empleadoToUpdate.getDireccion();
                    direccion.setNumero(body.getNumero());
                    empleadoToUpdate.setDireccion(direccion);
                }
                if(body.getPiso() != null){
                    Direccion direccion = empleadoToUpdate.getDireccion();
                    direccion.setPiso(body.getPiso());
                    empleadoToUpdate.setDireccion(direccion);
                }
                if(body.getLocalidadId() != null){
                    Direccion direccion = empleadoToUpdate.getDireccion();
                    Optional<Localidad> localidad = localidadRepository.findById(body.getLocalidadId());
                    if(localidad.isEmpty()){
                        return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Localidad id no existe!", localidad);
                    }
                    direccion.setLocalidad(localidad.get());
                    empleadoToUpdate.setDireccion(direccion);
                }

                empleadoRepository.save(empleadoToUpdate);

                EmpleadoResponseDTO response = new EmpleadoResponseDTO();
                response.setId(empleadoToUpdate.getId());
                response.setDni(empleadoToUpdate.getDni());
                response.setNombre(empleadoToUpdate.getNombre());
                response.setApellido(empleadoToUpdate.getApellido());
                Direccion direccion = empleadoToUpdate.getDireccion();
                Localidad localidad = direccion.getLocalidad();
                Provincia provincia = localidad.getProvincia();
                Pais pais = provincia.getPais();

                response.setDireccion(new DireccionResponseDTO(
                        direccion.getId(),
                        direccion.getCalle(),
                        direccion.getNumero(),
                        direccion.getPiso()
                ));
                response.setLocalidad(new LocalidadResponseDTO(
                        localidad.getId(),
                        localidad.getNombreLocalidad()
                ));
                response.setProvincia(new ProvinciaResponseDTO(
                        provincia.getId(),
                        provincia.getNombreProvincia()
                ));
                response.setPais(new PaisResponseDTO(
                        pais.getId(),
                        pais.getNombrePais()
                ));

                return ResponseHandler.responseBuilder(HttpStatus.OK, "Empleado actualizado con exito", response);
            } else {
                return ResponseHandler.responseBuilder(HttpStatus.CONFLICT, "Empleado no existe");
            }
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }
}

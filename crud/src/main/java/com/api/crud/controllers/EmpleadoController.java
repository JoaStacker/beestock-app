package com.api.crud.controllers;

import com.api.crud.services.IAuthService;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.response.EmpleadosResponseDTO;
import com.api.crud.services.models.response.ResponseDTO;
import com.api.crud.persistence.entities.Empleado;
import com.api.crud.services.IEmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/empleado")
public class EmpleadoController {

    @Autowired
    private IEmpleadoService empleadoService;
    @Autowired
    private IAuthService authService;

    @PostMapping("/")
    private ResponseEntity<Object> create(@RequestBody Empleado empleado) throws Exception {
        return new ResponseEntity<>(empleadoService.createEmpleado(empleado), HttpStatus.OK);
    }

    @GetMapping("/")
    private  ResponseEntity<Object> getAll() throws Exception {
        return new ResponseEntity<>(empleadoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> getOne(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(empleadoService.findOne(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Object> updateOne(@PathVariable("id") Long id, @RequestBody EmpleadoDTO empleado) throws Exception {
        return new ResponseEntity<>(empleadoService.updateEmpleado(id, empleado), HttpStatus.OK);
    }

}


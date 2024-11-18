package com.api.crud.controllers;

import com.api.crud.services.IAuthService;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.persistence.entities.Empleado;
import com.api.crud.services.IEmpleadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private IEmpleadoService empleadoService;

    @Autowired
    private IAuthService authService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/")
    private ResponseEntity<Object> create(@Valid @RequestBody EmpleadoDTO empleado) throws Exception {
        return new ResponseEntity<>(empleadoService.createEmpleado(empleado), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    private  ResponseEntity<Object> getAll() throws Exception {
        return new ResponseEntity<>(empleadoService.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}/")
    private ResponseEntity<Object> getOne(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(empleadoService.findOne(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}/")
    private ResponseEntity<Object> updateOne(@PathVariable("id") Long id, @Valid @RequestBody EmpleadoDTO empleado) throws Exception {
        return new ResponseEntity<>(empleadoService.updateEmpleado(id, empleado), HttpStatus.OK);
    }

}


package com.api.crud.controllers;

import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.HistorialPuesto;
import com.api.crud.services.IAuthService;
import com.api.crud.services.IEmpleadoService;
import com.api.crud.services.IHistorialPuestoService;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.dtos.HistorialPuestoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/historial-puestos")
public class HistorialPuestoController {

    @Autowired
    private IHistorialPuestoService historialPuestoService;

    @Autowired
    private IAuthService authService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/")
    private ResponseEntity<Object> create(@Valid @RequestBody HistorialPuestoDTO body) throws Exception {
        return new ResponseEntity<>(historialPuestoService.create(body), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}/")
    private ResponseEntity<Object> getAllByEmpleado(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(historialPuestoService.getAllByEmpleado(id), HttpStatus.OK);
    }

}


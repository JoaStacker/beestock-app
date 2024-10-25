package com.api.crud.controllers;

import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.services.IVentaService;
import com.api.crud.services.models.dtos.VentaDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")

public class VentaController {

    @Autowired
    IVentaService ventaService;

    @GetMapping
    private ResponseEntity<Object> getAllVentas() throws Exception {
        return new ResponseEntity<>(ventaService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    private ResponseEntity<Object> create(@Valid @RequestBody VentaDTO venta) throws Exception {
        return new ResponseEntity<>(ventaService.createVenta(venta), HttpStatus.OK);
    }

    @GetMapping("/{:id}")
    private ResponseEntity<Object> getOne(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(ventaService.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/cliente/{:id}")
    private ResponseEntity<Object> getVentasByCliente(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(ventaService.getAllByCliente(id), HttpStatus.OK);
    }

}


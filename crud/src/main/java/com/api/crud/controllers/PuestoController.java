package com.api.crud.controllers;

import com.api.crud.services.IPuestoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/puestos")
public class PuestoController {

    @Autowired
    private IPuestoService puestoService;

    @GetMapping
    private ResponseEntity<Object> getAll() throws Exception {
        return new ResponseEntity<>(puestoService.findAll(), HttpStatus.OK);
    }

}


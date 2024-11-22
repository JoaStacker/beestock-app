package com.api.crud.controllers;

import com.api.crud.services.IInteraccionService;
import com.api.crud.services.models.dtos.InteraccionDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interacciones")

public class InteraccionController {

    @Autowired
    IInteraccionService interaccionService;


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/")
    private ResponseEntity<Object> create(@Valid @RequestBody InteraccionDTO interaccion) throws Exception {
        return new ResponseEntity<>(interaccionService.create(interaccion), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}/")
    private ResponseEntity<Object> getOne(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(interaccionService.findOne(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/cliente/{id}/")
    private ResponseEntity<Object> getInteraccionesByCliente(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(interaccionService.getAllByCliente(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}/")
    private ResponseEntity<Object> updateOne(@PathVariable("id") Long id, @RequestBody InteraccionDTO interaccion) throws Exception {
        return new ResponseEntity<>(interaccionService.updateOne(id, interaccion), HttpStatus.OK);
    }

}

package com.api.crud.controllers;

import com.api.crud.services.models.IInteraccionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interaccion")

public class InteraccionController {

    @Autowired
    IInteraccionService interaccionService;


    @GetMapping("/{id}")
    private ResponseEntity<Object> getOne(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(interaccionService.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/cliente/{id}")
    private ResponseEntity<Object> getInteraccionByCliente(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(interaccionService.getAllByCliente(id), HttpStatus.OK);
    }



}

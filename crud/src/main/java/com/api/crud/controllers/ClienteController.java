package com.api.crud.controllers;

import com.api.crud.persistence.entities.Cliente;
import com.api.crud.services.IAuthService;
import com.api.crud.services.IClienteService;
import com.api.crud.services.models.dtos.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IAuthService authService;

    @PostMapping("")
    private ResponseEntity<Object> create(@RequestBody Cliente cliente) throws Exception {
        return new ResponseEntity<>(clienteService.createCliente(cliente), HttpStatus.OK);
    }

    @GetMapping("")
    private  ResponseEntity<Object> getAll() throws Exception {
        return new ResponseEntity<>(clienteService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<Object> getOne(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(clienteService.findOne(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<Object> updateOne(@PathVariable("id") Long id, @RequestBody ClienteDTO cliente) throws Exception {
        return new ResponseEntity<>(clienteService.updateCliente(id, cliente), HttpStatus.OK);
    }

    @PutMapping("/delete/{id}")
    private ResponseEntity<Object> deleteOne(@PathVariable("id") Long id, @RequestBody ClienteDTO cliente) throws Exception {
        return new ResponseEntity<>(clienteService.deleteCliente(id, cliente), HttpStatus.OK);
    }

    @GetMapping("/mes/{mesNacimiento}")
    private ResponseEntity<Object> getByMesNacimiento(@PathVariable("mesNacimiento") String mes) throws Exception {
        return new ResponseEntity<>(clienteService.findByMesNacimiento(mes), HttpStatus.OK);
    }

}


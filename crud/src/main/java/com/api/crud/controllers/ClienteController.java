package com.api.crud.controllers;

import com.api.crud.persistence.entities.Cliente;
import com.api.crud.services.IAuthService;
import com.api.crud.services.IClienteService;
import com.api.crud.services.models.dtos.ClienteDTO;
import com.api.crud.services.models.dtos.ProveedorDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;
    @Autowired
    private IAuthService authService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/")
    private ResponseEntity<Object> create(@Valid @RequestBody ClienteDTO body) throws Exception {
        return new ResponseEntity<>(clienteService.createCliente(body), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    private  ResponseEntity<Object> getAll() throws Exception {
        return new ResponseEntity<>(clienteService.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}/")
    private ResponseEntity<Object> getOne(@Valid @PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(clienteService.findOne(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/update/{id}/")
    private ResponseEntity<Object> updateOne(@Valid @PathVariable("id") Long id, @RequestBody ClienteDTO cliente) throws Exception {
        return new ResponseEntity<>(clienteService.updateCliente(id, cliente), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/delete/{id}/")
    private ResponseEntity<Object> deleteOne(@Valid @PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(clienteService.deleteCliente(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/mes/{mesNacimiento}/")
    private ResponseEntity<Object> getByMesNacimiento(@Valid @PathVariable("mesNacimiento") String mes) throws Exception {
        return new ResponseEntity<>(clienteService.findByMesNacimiento(mes), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/condiciones-tributarias/")
    private ResponseEntity<Object> getAllCondicionesTributarias() throws Exception {
        return new ResponseEntity<>(clienteService.findAllCondicionesTributarias(), HttpStatus.OK);
    }

}


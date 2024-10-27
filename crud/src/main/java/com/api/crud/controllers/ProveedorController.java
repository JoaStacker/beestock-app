package com.api.crud.controllers;

import com.api.crud.persistence.repositories.ITipoServicioRepository;
import com.api.crud.services.models.dtos.ClienteDTO;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.dtos.ProveedorDTO;
import com.api.crud.services.IProveedorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private IProveedorService proveedorServiceImpl;

    //POST
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/")
    public ResponseEntity<Object> create(@Valid @RequestBody ProveedorDTO proveedorDTO) throws Exception {
        return new ResponseEntity<>(proveedorServiceImpl.createProveedor(proveedorDTO), HttpStatus.OK);
    }
    //DELETE
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/delete/{id}/")
    public ResponseEntity<Object> eliminarProveedor(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(proveedorServiceImpl.deleteProveedorById(id), HttpStatus.OK);
    }
    //GET
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public ResponseEntity<Object> getAll() throws Exception {
        return new ResponseEntity<>(proveedorServiceImpl.findAll(), HttpStatus.OK);
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}/")
    private ResponseEntity<Object> getOne(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(proveedorServiceImpl.findOne(id), HttpStatus.OK);
    }

    //PUT
    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}/")
    private ResponseEntity<Object> updateOne(@PathVariable("id") Long id, @RequestBody ProveedorDTO proveedorDTO) throws Exception {
        return new ResponseEntity<>(proveedorServiceImpl.updateProveedor(id, proveedorDTO), HttpStatus.OK);
    }

    //GET
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/tipos-servicio/")
    public ResponseEntity<Object> getAllTiposServicios() throws Exception {
        return new ResponseEntity<>(proveedorServiceImpl.findAllTiposServicios(), HttpStatus.OK);
    }
}

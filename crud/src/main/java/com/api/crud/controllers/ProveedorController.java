package com.api.crud.controllers;

import com.api.crud.services.models.dtos.ProveedorDTO;
import com.api.crud.services.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProveedorController {

    @Autowired
    private IProveedorService proveedorServiceImpl;


    @PostMapping("/create")
    public ResponseEntity<Object> crearUsuario(@RequestBody ProveedorDTO proveedorDTO) throws Exception {
        return proveedorServiceImpl.createProveedor(proveedorDTO);
    }
}

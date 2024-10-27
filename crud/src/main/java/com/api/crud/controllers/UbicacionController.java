package com.api.crud.controllers;

import com.api.crud.services.IAuthService;
import com.api.crud.services.IClienteService;
import com.api.crud.services.IUbicacionService;
import com.api.crud.services.models.dtos.ClienteDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ubicacion")
public class UbicacionController {

    @Autowired
    private IUbicacionService ubicacionService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/paises/")
    private ResponseEntity<Object> getAllPaises() throws Exception {
        return new ResponseEntity<>(ubicacionService.getAllPaises(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/provincias/pais/{id}/")
    private  ResponseEntity<Object> getAllProvinciasByPais(@Valid @PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(ubicacionService.getAllProvinciasByPais(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/localidades/provincia/{id}/")
    private  ResponseEntity<Object> getAllLocalidadesByProvincia(@Valid @PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(ubicacionService.getAllLocalidadesByProvincia(id), HttpStatus.OK);
    }
}

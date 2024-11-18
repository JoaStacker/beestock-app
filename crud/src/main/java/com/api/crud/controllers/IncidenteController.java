package com.api.crud.controllers;

import com.api.crud.services.IIncidenteService;
import com.api.crud.services.IInteraccionService;
import com.api.crud.services.models.dtos.EmpleadoDTO;
import com.api.crud.services.models.dtos.IncidenteDTO;
import com.api.crud.services.models.dtos.InteraccionDTO;
import com.api.crud.services.models.response.incidentes.IncidenteResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/incidentes")

public class IncidenteController {

    @Autowired
    IIncidenteService incidenteService;

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/")
    private ResponseEntity<Object> create(@RequestBody IncidenteDTO body) throws Exception {
        return new ResponseEntity<>(incidenteService.createOne(body), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    private ResponseEntity<Object> getIncidentes() throws Exception {
        return new ResponseEntity<>(incidenteService.findAll(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/{id}/")
    private ResponseEntity<Object> getOne(@PathVariable("id") Long id) throws Exception {
        return new ResponseEntity<>(incidenteService.getOne(id), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/{id}/")
    private ResponseEntity<Object> updateOne(@PathVariable("id") Long id, @RequestBody IncidenteDTO body) throws Exception {
        return new ResponseEntity<>(incidenteService.updateOne(id, body), HttpStatus.OK);
    }

}

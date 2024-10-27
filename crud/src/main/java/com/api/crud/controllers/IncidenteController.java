package com.api.crud.controllers;

import com.api.crud.services.IIncidenteService;
import com.api.crud.services.IInteraccionService;
import com.api.crud.services.models.dtos.IncidenteDTO;
import com.api.crud.services.models.dtos.InteraccionDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/interacciones")

public class IncidenteController {

    @Autowired
    IIncidenteService incidenteService;


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    private ResponseEntity<Object> getIncidentes() throws Exception {
        return new ResponseEntity<>(incidenteService.findAll(), HttpStatus.OK);
    }

}

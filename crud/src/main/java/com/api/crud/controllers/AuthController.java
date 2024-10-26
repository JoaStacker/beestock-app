package com.api.crud.controllers;

import com.api.crud.services.models.dtos.*;
import com.api.crud.services.IAuthService;
import com.api.crud.services.models.dtos.SignupDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @PostMapping("/signup")
    private ResponseEntity<Object> signup(@Valid @RequestBody SignupDTO user) throws Exception {
        return new ResponseEntity<>(authService.signup(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    private ResponseEntity<Object> login(@Valid @RequestBody LoginDTO loginRequest) throws Exception {
        return authService.login(loginRequest);
    }
}

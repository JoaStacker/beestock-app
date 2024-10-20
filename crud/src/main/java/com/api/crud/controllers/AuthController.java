package com.api.crud.controllers;


import com.api.crud.services.models.response.*;
import com.api.crud.services.models.dtos.*;
import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.services.IAuthService;
import com.api.crud.services.models.response.ResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.UserResponseDTO;
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

    @PostMapping("/register")
    private ResponseEntity<ResponseDTO> addUser(@RequestBody UserEntity user) throws Exception {
        //ResponseEntity permite el uso de mensajes HttpStatus
        return new ResponseEntity<>(authService.register(user), HttpStatus.OK);
    }

    @PostMapping("/login")
    //en vez de un hash map devolver un  DTO
    private ResponseEntity<Object> login(@RequestBody LoginDTO loginRequest) throws Exception {
        return authService.login(loginRequest);
    }

//    private ResponseEntity<UserResponseDTO> login(@RequestBody LoginDTO loginRequest) throws Exception {
//        UserResponseDTO login = authService.login(loginRequest);
//        if(login.getEstado() == 200){
//
//            return new ResponseEntity<>(login, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(login, HttpStatus.UNAUTHORIZED);
//        }
//
//    }


}

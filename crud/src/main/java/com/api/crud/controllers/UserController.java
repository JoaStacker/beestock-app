/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.crud.controllers;

import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author ghino
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;


    @GetMapping("find-all")
    private ResponseEntity<List<UserEntity>> getAllUsers(){
        return new ResponseEntity<>(userService.findAllUsers(), HttpStatus.OK);
    }



}


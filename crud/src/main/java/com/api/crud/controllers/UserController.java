/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.crud.controllers;

import com.api.crud.models.UserModel;
import com.api.crud.services.UserService;
import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 *
 * @author ghino
 */
@RestController
@RequestMapping("/user")
public class UserController {


    //aqui se definen las peticiones http y las rutas
    @Autowired
    private UserService userService;
    
    @GetMapping
    public ArrayList<UserModel> getUsers(){
        return this.userService.getUser();
    }
    
    @PostMapping
    public UserModel saveUser(@RequestBody UserModel user){
        return this.userService.saveUser(user);
    }
     
    @GetMapping(path = "/{id}")
    public Optional<UserModel> getUserById(@PathVariable("id") Long id ){
        return this.userService.getById(id);
    }
    
    @PutMapping(path = "/{id}")
    public UserModel updateUserById(@RequestBody UserModel request,@PathVariable("id") long id){
        return this.userService.updateById(request, id);
    }
            
    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Long id){
        boolean ok=this.userService.deleteUser(id);
        if(ok){
             return "User with id" + id + "deleted!";
        }else{
            return "Error";
        }
    }
    
}


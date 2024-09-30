/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.crud.services;

import com.api.crud.models.UserModel;
import com.api.crud.repositories.IUserRepository;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author ghino
 */
@Service 
public class UserService {
    @Autowired
    IUserRepository userRepository;
    
    public ArrayList<UserModel> getUser(){
        return (ArrayList<UserModel>)userRepository.findAll();
    }
    /*
    Se crea una objeto de IUserRepo para poder usar metodos que salga del Jpa com
    el findall() de este metodo
    */
    public UserModel saveUser(UserModel user){
        return userRepository.save(user);
    }
    /*
    luego de obtener los usarios, hay que guardalos. Para eso esta el saveUser
    que hace uso de la clase Jpa
    */
    public Optional <UserModel> getById(Long id){
        return userRepository.findById(id);
        /*
        busca el usuario con un id determinado
        
        el tipo Optional nos dice que puede devolver algo o puede devolver null
        */
    }
    
    public UserModel updateById(UserModel request, Long id ){
        UserModel user=userRepository.findById(id).get();
        
        user.setFirstname(request.getFirstname());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        //el request es otro metodo de JPA.Aqui se setea el user a  partir de la request
        return user;
    }
    public Boolean deleteUser(Long id){
        try{
            userRepository.deleteById(id);
            return true;
        }catch(Exception e){
            return false;
        }
    }
}

package com.api.crud.services.models.validation;

import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.services.models.dtos.ResponseDTO;

public class UserValidation {
    //tambien se puede hacer mediante anotacioenes de jpa pero de esta forma se controla los mensajes
    public ResponseDTO validate(UserEntity user){
        ResponseDTO responseDTO = new ResponseDTO();
    //aqui van todas las validaciones necesarias para el usuario. Usar expresiones regulares
        responseDTO.setNumOfErrors(0);

        if(user.getEmail().isEmpty()){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors()+1);
            responseDTO.setMessage("it email is empty");
        }
        if(user.getPassword().isEmpty()){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors()+1);
            responseDTO.setMessage("it email is empty");
        }
        return responseDTO;
    }
//VAMOS A LAS CONFIGS Y CREAMOS LOS BEANS JUNTO CON LSO CORS
}

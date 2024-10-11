package com.api.crud.services.models.validation;

import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.services.models.response.ResponseDTO;
import org.springframework.stereotype.Component;

//aqui en vez de hacer est ode abajo. Podemos hacerlo mediante anotaciones de JPA en UserEntity
//pero de esta forma puedo controlar los mensajes de respuestas
//aqui van todas las validaciones necesarias para el usuario. Usar expresiones regulares
@Component //habra un error?
public class UserValidation {


    public ResponseDTO validate(UserEntity user){
        ResponseDTO responseDTO = new ResponseDTO();

        //esta validacion es para la creacion del usuario. No para el login




        responseDTO.setNumOfErrors(0);

        if(user.getEmail().isEmpty()){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors()+1);
            responseDTO.setMessage("it email is empty");
        }
        if(user.getPassword().isEmpty()){
            responseDTO.setNumOfErrors(responseDTO.getNumOfErrors()+1);
            responseDTO.setMessage("it password is empty");
        }
        return responseDTO;
    }
//VAMOS A LAS CONFIGS Y CREAMOS LOS BEANS JUNTO CON LOs CORS
}

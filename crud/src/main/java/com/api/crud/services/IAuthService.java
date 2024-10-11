package com.api.crud.services;

import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.services.models.dtos.LoginDTO;
import com.api.crud.services.models.response.ResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    public ResponseDTO register(UserEntity user) throws Exception;
    public ResponseEntity<Object> login(LoginDTO login) throws Exception;

}

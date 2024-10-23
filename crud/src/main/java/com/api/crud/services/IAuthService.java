package com.api.crud.services;

import com.api.crud.services.models.dtos.LoginDTO;
import com.api.crud.services.models.dtos.SignupDTO;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    public ResponseEntity<Object> login(LoginDTO login) throws Exception;
    public ResponseEntity<Object> signup(SignupDTO user) throws Exception;
}

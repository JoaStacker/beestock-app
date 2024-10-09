package com.api.crud.services;

import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.services.models.dtos.LoginDTO;
import com.api.crud.services.models.dtos.ResponseDTO;

import java.util.HashMap;

public interface IAuthService {
    public HashMap<String, String> login(LoginDTO login) throws Exception;
    public ResponseDTO register(UserEntity user) throws Exception;
}

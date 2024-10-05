package com.api.crud.services.impl;

import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.persistence.repositories.IUserRepository;
import com.api.crud.services.IAuthService;
import com.api.crud.services.IJWTUtilityService;
import com.api.crud.services.models.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Optional;

public class AuthServiceImpl implements IAuthService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IJWTUtilityService jwtUtilityService;
    @Autowired
    private UserValidation userValidation;

    public HashMap<String, String>login(LoginDTO login) throws Exception{
        try{
            HashMap<String, String> jwt = new HashMap<>();
            Optional<UserEntity> user=userRepository.findByEmail(login.getEmail());
            if(user.isPresent()){
                jwt.put("error"," User not registed!");
                return jwt;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

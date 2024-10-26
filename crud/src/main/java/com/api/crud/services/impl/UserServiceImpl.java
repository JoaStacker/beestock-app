package com.api.crud.services.impl;

import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.persistence.repositories.IUserRepository;
import com.api.crud.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserRepository userRepository;

    @Override
    public List<UserEntity> findAllUsers(){
        return userRepository.findAll();
    }
}

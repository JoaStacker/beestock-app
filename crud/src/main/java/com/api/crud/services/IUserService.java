package com.api.crud.services;

import com.api.crud.persistence.entities.UserEntity;

import java.util.List;

public interface IUserService {

    public List<UserEntity> findAllUsers();
}

package com.api.crud.services.impl;

import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.persistence.repositories.IUserRepository;//check
import com.api.crud.services.IAuthService;//check
import com.api.crud.services.IJWTUtilityService;
import com.api.crud.services.models.dtos.*;
import com.api.crud.services.models.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;//check
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Service
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
                jwt.put("error"," User not registered!");
                return jwt;
            }
            if(verifyPassword(login.getPassword(), user.get().getPassword())){
                jwt.put("jwt",jwtUtilityService.generateJWT(user.get().getId()));//reveer este metodo en IJWTUtilityService
            }else{
                jwt.put("error","Authentication failed");
            }
            return jwt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public ResponseDTO register(UserEntity user) throws Exception{
        try{
            ResponseDTO response = new ResponseDTO();


            if(response.getNumOfErrors()>0){
                return response;
            }
            List<UserEntity> getAllUsers=userRepository.findAll();

            for (UserEntity existingUser : getAllUsers) {
                if (existingUser.getEmail().equals(user.getEmail())) {
                    response.setMessage("Email already exists!");
                    return response;
                }
                // Agrega más comparaciones de campos relevantes según sea necesario.
            }


            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
            response.setMessage("User created succesfully!");
            return response;
        }catch(Exception e){
            throw new Exception(e.toString());
        }
    }

    private boolean verifyPassword(String enteredPassword,String storagePassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword,storagePassword);
    }
}

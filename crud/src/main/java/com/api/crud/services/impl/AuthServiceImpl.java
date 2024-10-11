package com.api.crud.services.impl;


import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.persistence.repositories.IUserRepository;//check
import com.api.crud.services.IAuthService;//check
import com.api.crud.services.IJWTUtilityService;
import com.api.crud.services.models.dtos.*;
import com.api.crud.services.models.response.ResponseDTO;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.UserResponseDTO;
import com.api.crud.services.models.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;//check
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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


    public ResponseEntity<Object> login(LoginDTO login) throws Exception{
        try{
            UserResponseDTO userResponseDTO = new UserResponseDTO();
            Optional<UserEntity> user=userRepository.findByEmail(login.getEmail());
            if(user.isEmpty()){
                return ResponseHandler.responseBuilder(HttpStatus.UNAUTHORIZED,
                        "User not registered!",userResponseDTO);
            }
            if(verifyPassword(login.getPassword(), user.get().getPassword())){
                userResponseDTO.setEmail(user.get().getEmail());
                userResponseDTO.setAdminClientes(user.get().getAdminClientes());
                userResponseDTO.setAdminVentas(user.get().getAdminVentas());
                userResponseDTO.setAdminProveedores(user.get().getAdminProveedores());
                userResponseDTO.setAdminRRHH(user.get().getAdminRRHH());
                userResponseDTO.setJwt(jwtUtilityService.generateJWT(user.get().getId()));
                return ResponseHandler.responseBuilder(HttpStatus.OK,
                        "User successfully logged in!!",userResponseDTO);
            }else{
                return ResponseHandler.responseBuilder(HttpStatus.UNAUTHORIZED,
                        "Authentication failed!",userResponseDTO);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//    public UserResponseDTO login(LoginDTO login) throws Exception{
//        try{
//            UserResponseDTO userResponseDTO = new UserResponseDTO();
//            Optional<UserEntity> user=userRepository.findByEmail(login.getEmail());
//            if(user.isEmpty()){
//                //se busca por correo, si no esta presente el correo, entonces no esta registrado
//                userResponseDTO.setMessage("User not registered!");
//                userResponseDTO.setEstado(401);
//                return userResponseDTO;
//                }
//            if(verifyPassword(login.getPassword(), user.get().getPassword())){
//                userResponseDTO.setMessage("User successfully logged in!");
//                userResponseDTO.setEstado(200);
//                userResponseDTO.setEmail(user.get().getEmail());
//                userResponseDTO.setAdminClientes(user.get().getAdminClientes());
//                userResponseDTO.setAdminVentas(user.get().getAdminVentas());
//                userResponseDTO.setAdminProveedores(user.get().getAdminProveedores());
//                userResponseDTO.setAdminRRHH(user.get().getAdminRRHH());
//                userResponseDTO.setJwt(jwtUtilityService.generateJWT(user.get().getId()));
//                return userResponseDTO;
//            }else{
//                userResponseDTO.setMessage("Authentication failed!");
//                userResponseDTO.setEstado(401);
//                return userResponseDTO;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }







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

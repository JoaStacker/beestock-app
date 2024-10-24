package com.api.crud.services.impl;

import com.api.crud.persistence.entities.UserEntity;
import com.api.crud.persistence.repositories.IUserRepository;//check
import com.api.crud.services.IAuthService;//check
import com.api.crud.services.IJWTUtilityService;
import com.api.crud.services.models.dtos.*;
import com.api.crud.services.models.response.ResponseHandler;
import com.api.crud.services.models.response.UserResponseDTO;
import com.api.crud.services.models.dtos.SignupDTO;
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

    public ResponseEntity<Object> signup(SignupDTO body) throws Exception{
        try{
            List<UserEntity> getAllUsers=userRepository.findAll();

            for (UserEntity existingUser : getAllUsers) {
                if (existingUser.getEmail().equals(body.getEmail())) {
                    return ResponseHandler.responseBuilder(HttpStatus.CONFLICT,
                            "El email ya existe!");
                }
            }

            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
            UserEntity user = new UserEntity();
            user.setEmail(body.getEmail());
            user.setPassword(encoder.encode(body.getPassword()));
            user.setAdminClientes(body.getAdminClientes());
            user.setAdminProveedores(body.getAdminProveedores());
            user.setAdminRRHH(body.getAdminRRHH());
            user.setAdminVentas(body.getAdminVentas());

            userRepository.save(user);

            return ResponseHandler.responseBuilder(HttpStatus.CONFLICT,
                    "Usuario creado con exito!");
        }catch(Exception e){
            return ResponseHandler.responseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear Proveedor: "
                    + e.getMessage());
        }
    }

    private boolean verifyPassword(String enteredPassword,String storagePassword){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(enteredPassword,storagePassword);
    }
}

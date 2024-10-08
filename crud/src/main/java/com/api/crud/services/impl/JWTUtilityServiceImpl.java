package com.api.crud.services.impl;



import com.api.crud.services.IJWTUtilityService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import  org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;

@Service
public class JWTUtilityServiceImpl implements IJWTUtilityService {



    //creo esto para poder tratar con las llaves puesto que primer onecesito leerlas
    @Value("classpath:jwtKeys/private_key.pem")
    private Resource privateKeyResource;
    @Value("classpath:jwtKeys/public_key.pem")
    private Resource publicKeyResource;
    //ya con los cmetodos de más abajo que  nos permite leer las keys podemos crear los metodos que generan
    // los jwt y su validacion
    @Override
    public String generateJWT(Long userId) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        PrivateKey privateKey = loadPrivateKey(privateKeyResource);
        JWSSigner signer = new RSASSASigner(privateKey);
        Date now = new Date();
        JWTClaimsSet claimsSet=new JWTClaimsSet.Builder()
                .subject(userId.toString())
                .issueTime(now)
                .expirationTime(new Date(now.getTime()*14400000))
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256),claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }

    @Override
    public JWTClaimsSet parseJWT(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException {
        PublicKey publicKey=loadPublicKey(publicKeyResource);
        SignedJWT signedJWT= SignedJWT.parse(jwt);
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
        if(!signedJWT.verify(verifier)){
            throw new JOSEException("Invalid signature");
        }
        JWTClaimsSet claimsSet=signedJWT.getJWTClaimsSet();
        if(claimsSet.getExpirationTime().before(new Date())){
             throw new JOSEException("Expired JWT");
        }

         return signedJWT.getJWTClaimsSet();
    }



//PRIMERO ESTO, me permite leer las llaves
private PrivateKey loadPrivateKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    // 1. Leer el archivo que contiene la llave privada
    byte[] keyBytes = Files.readAllBytes(Paths.get(resource.getURI()));

    // 2. Convertir el contenido del archivo en un String
    String privateKeyString = new String(keyBytes, StandardCharsets.UTF_8)

            // 3. Limpiar el string eliminando los delimitadores de la clave privada
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")

            // 4. Eliminar todos los espacios en blanco (incluidos saltos de línea)
            .replaceAll("\\s", "");

    // 5. Decodificar el String Base64 para obtener los bytes de la llave privada
    byte[] decodedKey = Base64.getDecoder().decode(privateKeyString);

    // 6. Crear un KeyFactory para el algoritmo RSA
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");

    // 7. Generar un objeto PrivateKey a partir de los bytes decodificados
    return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedKey));
}


    private PublicKey loadPublicKey(Resource resource) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] keyBytes= Files.readAllBytes(Paths.get(resource.getURI()));
        String publicKeyString = new String(keyBytes, StandardCharsets.UTF_8)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s","");
        byte[] decodedKey= Base64.getDecoder().decode(publicKeyString);
        KeyFactory keyFactory= KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(new PKCS8EncodedKeySpec(decodedKey));
    }
}

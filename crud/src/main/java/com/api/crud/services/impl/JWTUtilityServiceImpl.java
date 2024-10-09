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
//se crea y se valida los jwt con las llaves


    //creo esto para poder tratar con las llaves puesto que primer onecesito leerlas
    @Value("classpath:jwtKeys/private_key.pem")
    private Resource privateKeyResource;
    @Value("classpath:jwtKeys/public_key.pem")
    private Resource publicKeyResource;
    //ya con los cmetodos de más abajo que  nos permite leer las keys podemos crear los metodos que generan
    // los jwt y su validacion
    @Override
    public String generateJWT(Long userId) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, JOSEException {
        // 1. Cargar la llave privada para firmar el JWT
        PrivateKey privateKey = loadPrivateKey(privateKeyResource);

        // 2. Crear un firmador con la llave privada usando el algoritmo RSA
        JWSSigner signer = new RSASSASigner(privateKey);

        // 3. Establecer la fecha y hora actual
        Date now = new Date();

        // 4. Crear el conjunto de claims del JWT (cuerpo del token)
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userId.toString())           // El "subject" (sub) es el ID del usuario. Puede ser el email?
                .issueTime(now)                       // Fecha en la que se emite el token
                .expirationTime(new Date(now.getTime() + 14400000))  // Fecha de expiración (aquí 4 horas)
                .build();

        // 5. Crear un JWT firmado con el algoritmo RS256 (RSA con SHA-256)
        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);

        // 6. Firmar el JWT con la llave privada usando el firmador configurado
        signedJWT.sign(signer);

        // 7. Serializar el JWT firmado (convertirlo a un string para enviarlo)
        return signedJWT.serialize();
    }


    @Override
    public JWTClaimsSet parseJWT(String jwt) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, ParseException, JOSEException {
        // 1. Cargar la llave pública desde un recurso
        PublicKey publicKey = loadPublicKey(publicKeyResource);

        // 2. Parsear el JWT recibido (cadena en formato compact)
        SignedJWT signedJWT = SignedJWT.parse(jwt);

        // 3. Crear un verificador para verificar la firma con la llave pública
        JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey) publicKey);
        //3.5 es decir que hasta aqui tengo la jwt enviado y acabo de carga la llave publica. Luego verifico si es valida
        // 4. Verificar la firma del JWT
        if (!signedJWT.verify(verifier)) {
            throw new JOSEException("Invalid signature"); // Firma no válida
        }

        // 5. Obtener los claims del JWT
        JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

        // 6. Comprobar si el JWT ha expirado
        if (claimsSet.getExpirationTime().before(new Date())) {
            throw new JOSEException("Expired JWT"); // El JWT ha expirado
        }

        // 7. Devolver los claims del JWT si la firma es válida y no ha expirado
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

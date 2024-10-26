package com.api.crud.security;

import com.api.crud.services.IJWTUtilityService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;
import java.util.Collections;


public class JWTAuthorizationFilter extends OncePerRequestFilter {

    //@Autowired   tal vez haga falta pero hay un constructor ya creado
    IJWTUtilityService jwtUtilityService;

    // Constructor que permite la inyección de la implementación de IJWTUtilityService.
    public JWTAuthorizationFilter(IJWTUtilityService jwtUtilityService) {
        this.jwtUtilityService = jwtUtilityService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");//no tiene que ser name:   ?
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // Continúa sin autenticación.
            return;
        }

        String token = header.substring(7); // Extrae el token JWT.
        try {
            // Valida el token y obtiene los reclamos (claims).
            JWTClaimsSet claims = jwtUtilityService.parseJWT(token);
            // Crea el objeto de autenticación.
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(claims.getSubject(), null, Collections.emptyList());
            // Establece la autenticación en el contexto de seguridad.
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    filterChain.doFilter(request, response);
    }
}

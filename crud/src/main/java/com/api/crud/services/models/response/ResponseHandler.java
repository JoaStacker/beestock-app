package com.api.crud.services.models.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> responseBuilder(HttpStatus httpStatus, String message, Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", httpStatus);
        response.put("message", message);
        response.put("data", data);
        return new ResponseEntity<>(response, httpStatus);
    }

    public static ResponseEntity<Object> responseBuilder(HttpStatus httpStatus, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", httpStatus);
        response.put("message", message);
        response.put("data", null);
        return new ResponseEntity<>(response, httpStatus);
    }
}

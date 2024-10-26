package com.api.crud.services.models.response;

public class UserResponseDTO {
    private String email;
    private Boolean adminRRHH;
    private Boolean adminClientes;
    private Boolean adminProveedores;
    private Boolean adminVentas;
    private String jwt;

    public UserResponseDTO() {
    }

    public UserResponseDTO(String email, Boolean adminRRHH, Boolean adminClientes, Boolean adminProveedores, Boolean adminVentas, String jwt) {
        this.email = email;
        this.adminRRHH = adminRRHH;
        this.adminClientes = adminClientes;
        this.adminProveedores = adminProveedores;
        this.adminVentas = adminVentas;
        this.jwt = jwt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdminRRHH() {
        return adminRRHH;
    }

    public void setAdminRRHH(Boolean adminRRHH) {
        this.adminRRHH = adminRRHH;
    }

    public Boolean getAdminClientes() {
        return adminClientes;
    }

    public void setAdminClientes(Boolean adminClientes) {
        this.adminClientes = adminClientes;
    }

    public Boolean getAdminProveedores() {
        return adminProveedores;
    }

    public void setAdminProveedores(Boolean adminProveedores) {
        this.adminProveedores = adminProveedores;
    }

    public Boolean getAdminVentas() {
        return adminVentas;
    }

    public void setAdminVentas(Boolean adminVentas) {
        this.adminVentas = adminVentas;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
    //    private String email;
//    private Boolean adminRRHH;
//    private Boolean adminClientes;
//    private Boolean adminProveedores;
//    private Boolean adminVentas;
//    private String jwt;
//    private String message;
//    private int estado;
//
//
//    public UserResponseDTO() {
//    }
//
//    public UserResponseDTO(String message, int estado) {
//        this.message = message;
//        this.estado = estado;
//    }
//
//    public UserResponseDTO(String email, Boolean adminRRHH, Boolean adminClientes, Boolean adminProveedores, Boolean adminVentas, String jwt) {
//        this.email = email;
//        this.adminRRHH = adminRRHH;
//        this.adminClientes = adminClientes;
//        this.adminProveedores = adminProveedores;
//        this.adminVentas = adminVentas;
//        this.jwt = jwt;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Boolean getAdminRRHH() {
//        return adminRRHH;
//    }
//
//    public void setAdminRRHH(Boolean adminRRHH) {
//        this.adminRRHH = adminRRHH;
//    }
//
//    public Boolean getAdminClientes() {
//        return adminClientes;
//    }
//
//    public void setAdminClientes(Boolean adminClientes) {
//        this.adminClientes = adminClientes;
//    }
//
//    public Boolean getAdminProveedores() {
//        return adminProveedores;
//    }
//
//    public void setAdminProveedores(Boolean adminProveedores) {
//        this.adminProveedores = adminProveedores;
//    }
//
//    public Boolean getAdminVentas() {
//        return adminVentas;
//    }
//
//    public void setAdminVentas(Boolean adminVentas) {
//        this.adminVentas = adminVentas;
//    }
//
//    public String getJwt() {
//        return jwt;
//    }
//
//    public void setJwt(String jwt) {
//        this.jwt = jwt;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public int getEstado() {
//        return estado;
//    }
//
//    public void setEstado(int estado) {
//        this.estado = estado;
//    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.crud.services.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL) // Excluir campos nulos durante la serialización
public class SignupDTO {

    @NotNull
    @JsonProperty(required = true)
    private String email;

    @NotNull
    @JsonProperty(required = true)
    private String password;

    private Boolean adminRRHH;

    private Boolean adminClientes;

    private Boolean adminProveedores;

    private Boolean adminVentas;

    public SignupDTO() {
    }

    public SignupDTO(String email, String password, Boolean adminRRHH, Boolean adminClientes, Boolean adminProveedores, Boolean adminVentas) {
        this.email = email;
        this.password = password;
        this.adminRRHH = adminRRHH;
        this.adminClientes = adminClientes;
        this.adminProveedores = adminProveedores;
        this.adminVentas = adminVentas;
    }

    public @NotNull String getEmail() {
        return email;
    }

    public void setEmail(@NotNull String email) {
        this.email = email;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
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
}

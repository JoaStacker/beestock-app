/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.api.crud.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user")

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private Boolean adminRRHH;

    @Column
    private Boolean adminClientes;

    @Column
    private Boolean adminProveedores;

    @Column
    private Boolean adminVentas;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", referencedColumnName = "id")
    private Empleado empleado;

    public UserEntity() {
    }

    public UserEntity(Long id, String email, String password, Boolean adminRRHH, Boolean adminClientes, Boolean adminProveedores, Boolean adminVentas, Empleado empleado) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.adminRRHH = adminRRHH;
        this.adminClientes = adminClientes;
        this.adminProveedores = adminProveedores;
        this.adminVentas = adminVentas;
        this.empleado = empleado;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

}

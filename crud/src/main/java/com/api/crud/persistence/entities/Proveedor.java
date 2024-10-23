package com.api.crud.persistence.entities;


import jakarta.persistence.*;
import  com.api.crud.persistence.entities.TipoServicio;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;
    private String CUIT;
    private String correo;

    // Un proveedor puede tener muchos servicios
    @OneToMany(mappedBy = "proveedor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TipoServicio> TipoServicios = new ArrayList<>();
    // Un proveedor tiene una sola dirección
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private Direccion direccion;

    // Getters y Setters

    public Proveedor() {
    }

    public Proveedor(String nombre, String CUIT, String correo, List<TipoServicio> tipoServicios, Direccion direccion) {
        this.nombre = nombre;
        this.CUIT = CUIT;
        this.correo = correo;
        TipoServicios = tipoServicios;
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCUIT() {
        return CUIT;
    }

    public void setCUIT(String CUIT) {
        this.CUIT = CUIT;
    }

    public List<TipoServicio> getTipoServicios() {
        return TipoServicios;
    }

    public void setTipoServicios(List<TipoServicio> tipoServicios) {
        TipoServicios = tipoServicios;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }
}

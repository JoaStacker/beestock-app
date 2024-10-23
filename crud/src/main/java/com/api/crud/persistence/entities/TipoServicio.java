package com.api.crud.persistence.entities;

import jakarta.persistence.*;

@Entity
public class TipoServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    // Muchos servicios están asociados a un solo proveedor
    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;
    // Getters y Setters
    public TipoServicio() {}

    public TipoServicio(String nombre, Proveedor proveedor) {
        this.nombre = nombre;
        this.proveedor = proveedor;
    }

    public TipoServicio(String nombre) {
        this.nombre = nombre;
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

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}

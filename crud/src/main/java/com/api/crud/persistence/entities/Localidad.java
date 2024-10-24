package com.api.crud.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "localidad")

public class Localidad{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombreLocalidad;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "provincia_id", nullable = false) // Clave foránea
    private Provincia provincia;

    public Localidad() {
    }

    public Localidad(String nombreLocalidad, Provincia provincia) {
        this.nombreLocalidad = nombreLocalidad;
        this.provincia = provincia;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreLocalidad() {
        return nombreLocalidad;
    }

    public void setNombreLocalidad(String nombreLocalidad){
        this.nombreLocalidad = nombreLocalidad;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }
}

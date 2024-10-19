package com.api.crud.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "direccion")

public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String calle;

    @Column
    private String numero;

    @Column
    private String piso;

    @ManyToOne
    @JoinColumn(name = "localidad_id", nullable = false) // Clave foránea
    private Localidad localidad;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false) // Clave foránea
    private Empleado empleado;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}

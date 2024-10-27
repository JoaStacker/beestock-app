package com.api.crud.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incidente")

public class Incidente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String descripcion;

    @Column
    private LocalDateTime fechaIncidente;

    @Column
    private LocalDateTime fechaSolucion = null;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id", nullable = false) // Clave foránea
    private Proveedor proveedor;

    public Incidente(){
    }

    public Incidente(String descripcion, LocalDateTime fechaIncidente, LocalDateTime fechaSolucion, Proveedor proveedor) {
        this.descripcion = descripcion;
        this.fechaIncidente = fechaIncidente;
        this.fechaSolucion = fechaSolucion;
        this.proveedor = proveedor;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFechaIncidente() {
        return fechaIncidente;
    }

    public void setFechaIncidente(LocalDateTime fechaIncidente) {
        this.fechaIncidente = fechaIncidente;
    }

    public LocalDateTime getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(LocalDateTime fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}

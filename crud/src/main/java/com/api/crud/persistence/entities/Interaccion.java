package com.api.crud.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "interaccion")

public class Interaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime fechaInteraccion;

    @Column
    private String medio;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false) // Clave foránea
    private Cliente cliente;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "empleado_id", nullable = false) // Clave foránea
    private Empleado empleado;

    public Interaccion(){
    }

    public Interaccion(LocalDateTime fechaInteraccion, String medio, Cliente cliente, Empleado empleado) {
        this.fechaInteraccion = fechaInteraccion;
        this.medio = medio;
        this.cliente = cliente;
        this.empleado = empleado;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFechaInteraccion() {
        return fechaInteraccion;
    }

    public void setFechaInteraccion(LocalDateTime fechaInteraccion) {
        this.fechaInteraccion = fechaInteraccion;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}

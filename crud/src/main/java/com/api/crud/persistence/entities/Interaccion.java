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

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false) // Clave foránea

    private Cliente cliente;


    public Interaccion(){
    }

    public Interaccion(LocalDateTime fechaInteraccion, Cliente cliente) {
        this.fechaInteraccion = fechaInteraccion;
        this.cliente = cliente;
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
}

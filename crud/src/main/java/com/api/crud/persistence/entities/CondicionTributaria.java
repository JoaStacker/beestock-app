package com.api.crud.persistence.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "condicionTributaria")

public class CondicionTributaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String tipo;

    @OneToOne
    @JoinColumn(name = "cliente_id") // Nombre de la columna en la tabla CondicionTributaria
    private Cliente cliente;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}

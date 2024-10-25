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



    // Getters y Setters
    public CondicionTributaria(){
    }

    public CondicionTributaria(String tipo) {
        this.tipo = tipo;
    }

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

}

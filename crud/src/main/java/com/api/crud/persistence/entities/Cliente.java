package com.api.crud.persistence.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "cliente")

public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String cuit;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String email;

    @Column
    private LocalDateTime fechaNacimiento;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "condicion_tributaria_id", referencedColumnName = "id") // Clave foránea
    private CondicionTributaria condicionTributaria;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private Direccion direccion;


    @Column
    private Boolean borrado = false;

    // Getters y Setters
    public Cliente() {}

    public Cliente(String cuit, String nombre, String apellido, String email, LocalDateTime fechaNacimiento, CondicionTributaria condicionTributaria, String calle, String numero, String piso, Localidad localidad) {
        this.cuit = cuit;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.condicionTributaria = condicionTributaria;
        this.direccion = new Direccion(calle, numero, piso, localidad);

    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public CondicionTributaria getCondicionTributaria() {
        return condicionTributaria;
    }

    public void setCondicionTributaria(CondicionTributaria condicionTributaria) {
        this.condicionTributaria = condicionTributaria;
    }


    public Boolean getBorrado() {
        return borrado;
    }

    public void setBorrado(Boolean borrado) {
        this.borrado = borrado;
    }
}

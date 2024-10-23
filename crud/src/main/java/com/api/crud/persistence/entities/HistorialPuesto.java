package com.api.crud.persistence.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_puesto")

public class HistorialPuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime fechaIngreso;

    @Column
    private LocalDateTime fechaSalida;

    @ManyToOne
    @JoinColumn(name = "empleado_id", nullable = false) // Clave foránea
    private Empleado empleado;

    @ManyToOne
    @JoinColumn(name = "puesto_id", nullable = false) // Clave foránea
    private Puesto puesto;

    public HistorialPuesto() {
    }

    public HistorialPuesto(Long id, LocalDateTime fechaIngreso, LocalDateTime fechaSalida, Empleado empleado, Puesto puesto) {
        this.id = id;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.empleado = empleado;
        this.puesto = puesto;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }
}

package com.api.crud.persistence.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "proveedor")

public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;
    private String cuit;
    private String correo;
    private boolean estado;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name="proveedor_tipo_servicio", joinColumns = @JoinColumn(name="id_tipo_servicio", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="id_proveedor", referencedColumnName = "id")
    )
    private List<TipoServicio> tipoServicios;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private Direccion direccion;

    @OneToMany(mappedBy = "proveedor", fetch = FetchType.LAZY)
    private List<Incidente> incidentes;

    // Getters y Setters
    public Proveedor() {
    }

    public Proveedor(String nombre, String cuit, String correo, String calle, String numero, String piso, Localidad localidad) {
        this.nombre = nombre;
        this.cuit = cuit;
        this.correo = correo;
        this.direccion = new Direccion(calle, numero, piso, localidad);
        estado = true;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<TipoServicio> getTipoServicios() {
        return tipoServicios;
    }

    public void setTipoServicios(List<TipoServicio> tipoServicios) {
        this.tipoServicios = tipoServicios;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<Incidente> getIncidentes() {
        return incidentes;
    }

    public void setIncidentes(List<Incidente> incidentes) {
        this.incidentes = incidentes;
    }
}

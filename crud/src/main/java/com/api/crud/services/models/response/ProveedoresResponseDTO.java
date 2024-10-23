package com.api.crud.services.models.response;

import com.api.crud.persistence.entities.*;

import java.util.List;

public class ProveedoresResponseDTO {
    private String correo;
    private String nombre;
    private String cuit;
    private Direccion direccion;
    private List<TipoServicio> tipoServicio;


    public ProveedoresResponseDTO() {}

    public ProveedoresResponseDTO(String correo, String nombre, String cuit, Direccion direccion, List<TipoServicio> tipoServicio) {
        this.correo = correo;
        this.nombre = nombre;
        this.cuit = cuit;
        this.direccion = direccion;
        this.tipoServicio = tipoServicio;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCUIT() {
        return cuit;
    }

    public void setCUIT(String cuit) {
        this.cuit = cuit;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public List<TipoServicio> getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(List<TipoServicio> tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
}

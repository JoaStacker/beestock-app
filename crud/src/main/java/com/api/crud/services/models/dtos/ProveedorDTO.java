package com.api.crud.services.models.dtos;

import com.api.crud.persistence.entities.TipoServicio;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL) // Excluir campos nulos durante la serialización
public class ProveedorDTO {

    private String nombre;
    private String cuit;
    private String correo;
    private String numero;
    private String calle;
    private String piso;
    private String localidad;
    private String provincia;
    private String pais;
    private List<TipoServicio> tipoServicio;

    // Getters y Setters


    public List<TipoServicio> getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(List<TipoServicio> tipoServicio) {
        this.tipoServicio = tipoServicio;
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}

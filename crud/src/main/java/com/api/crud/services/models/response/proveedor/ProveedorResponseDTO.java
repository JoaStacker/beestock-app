package com.api.crud.services.models.response.proveedor;

import com.api.crud.persistence.entities.*;
import com.api.crud.services.models.response.CondicionTributaria.CondicionTributariaResponseDTO;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.direccion.LocalidadResponseDTO;
import com.api.crud.services.models.response.direccion.PaisResponseDTO;
import com.api.crud.services.models.response.direccion.ProvinciaResponseDTO;

import java.util.List;

public class ProveedorResponseDTO {

    private String nombre;
    private String cuit;
    private String correo;
    private List<TipoServicio> tipoServicios;
    private DireccionResponseDTO direccion;
    //private CondicionTributariaResponseDTO condicionTributaria;
    private LocalidadResponseDTO localidad;
    private ProvinciaResponseDTO provincia;
    private PaisResponseDTO pais;

    public ProveedorResponseDTO() {}

    public ProveedorResponseDTO(String nombre, String cuit, String correo, List<TipoServicio> tipoServicios,
                                DireccionResponseDTO direccion, LocalidadResponseDTO localidad,
                                ProvinciaResponseDTO provincia, PaisResponseDTO pais) {
        this.nombre = nombre;
        this.cuit = cuit;
        this.correo = correo;
        this.tipoServicios = tipoServicios;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
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

    public DireccionResponseDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionResponseDTO direccion) {
        this.direccion = direccion;
    }

    public LocalidadResponseDTO getLocalidad() {
        return localidad;
    }

    public void setLocalidad(LocalidadResponseDTO localidad) {
        this.localidad = localidad;
    }

    public ProvinciaResponseDTO getProvincia() {
        return provincia;
    }

    public void setProvincia(ProvinciaResponseDTO provincia) {
        this.provincia = provincia;
    }

    public PaisResponseDTO getPais() {
        return pais;
    }

    public void setPais(PaisResponseDTO pais) {
        this.pais = pais;
    }
}

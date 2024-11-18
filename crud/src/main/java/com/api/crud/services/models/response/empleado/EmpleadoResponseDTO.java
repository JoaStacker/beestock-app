package com.api.crud.services.models.response.empleado;

import com.api.crud.persistence.entities.Empleado;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.direccion.LocalidadResponseDTO;
import com.api.crud.services.models.response.direccion.PaisResponseDTO;
import com.api.crud.services.models.response.direccion.ProvinciaResponseDTO;
import com.api.crud.services.models.response.puesto.PuestoResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class EmpleadoResponseDTO {
    private Long id;
    private String dni;
    private String nombre;
    private String apellido;
    private String email;
    private DireccionResponseDTO direccion;
    private LocalidadResponseDTO localidad;
    private ProvinciaResponseDTO provincia;
    private PaisResponseDTO pais;
    private String puesto;

    public EmpleadoResponseDTO() {
    }

    public EmpleadoResponseDTO(Long id, String dni, String nombre, String apellido, String email) {
        this.id = id;
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.apellido = apellido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
}

package com.api.crud.services.models.response.Cliente;

import com.api.crud.persistence.entities.TipoServicio;
import com.api.crud.services.models.response.CondicionTributaria.CondicionTributariaResponseDTO;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.direccion.LocalidadResponseDTO;
import com.api.crud.services.models.response.direccion.PaisResponseDTO;
import com.api.crud.services.models.response.direccion.ProvinciaResponseDTO;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ClienteResponseDTO {

    private Long id;
    private String cuit;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDateTime fechaNacimiento;
    private DireccionResponseDTO direccion;
    private CondicionTributariaResponseDTO condicionTributaria;
    private LocalidadResponseDTO localidad;
    private ProvinciaResponseDTO provincia;
    private PaisResponseDTO pais;

    public ClienteResponseDTO() {}

    public ClienteResponseDTO(Long id, String cuit, String nombre, String apellido, String email, LocalDateTime fechaNacimiento, CondicionTributariaResponseDTO condicionTributaria, DireccionResponseDTO direccion, LocalidadResponseDTO localidad, ProvinciaResponseDTO provincia, PaisResponseDTO pais) {
        this.id = id;
        this.cuit = cuit;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.direccion = direccion;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
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

    public CondicionTributariaResponseDTO getCondicionTributaria() {
        return condicionTributaria;
    }

    public void setCondicionTributaria(CondicionTributariaResponseDTO condicionTributaria) {
        this.condicionTributaria = condicionTributaria;
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

package com.api.crud.services.models.response.interaccion;

import com.api.crud.persistence.entities.Cliente;
import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.TipoServicio;
import com.api.crud.services.models.response.Cliente.ClienteResponseDTO;
import com.api.crud.services.models.response.ClientesResponseDTO;
import com.api.crud.services.models.response.direccion.DireccionResponseDTO;
import com.api.crud.services.models.response.direccion.LocalidadResponseDTO;
import com.api.crud.services.models.response.direccion.PaisResponseDTO;
import com.api.crud.services.models.response.direccion.ProvinciaResponseDTO;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public class InteraccionResponseDTO {
    private Long id;
    private LocalDateTime fechaInteraccion;
    private String medio;
    private ClienteResponseDTO cliente;
    private EmpleadoResponseDTO empleado;
    private String setEmpleadoNombre;

    public InteraccionResponseDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaInteraccion() {
        return fechaInteraccion;
    }

    public void setFechaInteraccion(LocalDateTime fechaInteraccion) {
        this.fechaInteraccion = fechaInteraccion;
    }

    public String getMedio() {
        return medio;
    }

    public void setMedio(String medio) {
        this.medio = medio;
    }

    public ClienteResponseDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteResponseDTO cliente) {
        this.cliente = cliente;
    }

    public EmpleadoResponseDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoResponseDTO empleado) {
        this.empleado = empleado;
    }

    public String getSetEmpleadoNombre() {
        return setEmpleadoNombre;
    }

    public void setSetEmpleadoNombre(String setEmpleadoNombre) {
        this.setEmpleadoNombre = setEmpleadoNombre;
    }
}

package com.api.crud.services.models.response.ventas;

import com.api.crud.services.models.dtos.DetalleVentaDTO;
import com.api.crud.services.models.response.Cliente.ClienteResponseDTO;
import com.api.crud.services.models.response.empleado.EmpleadoResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public class VentaResponseDTO {
    private Long id;
    private LocalDateTime fechaVenta;
    private Float montoTotal;
    private Long cantidadCuotas;
    private Long estado;
    private String estadoNombre;
    private String clienteNombre;
    private EmpleadoResponseDTO empleado;
    private ClienteResponseDTO cliente;
    private List<DetalleVentaDTO> detallesVenta;

    public VentaResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Long getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(Long cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public EmpleadoResponseDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoResponseDTO empleado) {
        this.empleado = empleado;
    }

    public ClienteResponseDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteResponseDTO cliente) {
        this.cliente = cliente;
    }

    public List<DetalleVentaDTO> getDetallesVenta() {
        return detallesVenta;
    }

    public void setDetallesVenta(List<DetalleVentaDTO> detallesVenta) {
        this.detallesVenta = detallesVenta;
    }

    public String getEstadoNombre() {
        return estadoNombre;
    }

    public void setEstadoNombre(String estadoNombre) {
        this.estadoNombre = estadoNombre;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }
}

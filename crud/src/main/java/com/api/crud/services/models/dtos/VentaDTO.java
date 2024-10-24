package com.api.crud.services.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL) // Excluir campos nulos durante la serialización
public class VentaDTO {

    @NotNull
    @JsonProperty(required = true)
    private LocalDateTime fechaVenta;

    @NotNull
    @JsonProperty(required = true)
    private Float montoTotal;

    @NotNull
    @JsonProperty(required = true)
    private Long cantidadCuotas;

    @NotNull
    @JsonProperty(required = true)
    private Long estado;

    @NotNull
    @JsonProperty(required = true)
    private Long empleadoId;

    @NotNull
    @JsonProperty(required = true)
    private Long clienteId;

    // TODO
//    @NotNull
//    @JsonProperty(required = true)
//    private List<DetalleVentaDTO> detallesVenta;

    public @NotNull LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(@NotNull LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public @NotNull Float getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(@NotNull Float montoTotal) {
        this.montoTotal = montoTotal;
    }

    public @NotNull Long getCantidadCuotas() {
        return cantidadCuotas;
    }

    public void setCantidadCuotas(@NotNull Long cantidadCuotas) {
        this.cantidadCuotas = cantidadCuotas;
    }

    public @NotNull Long getEstado() {
        return estado;
    }

    public void setEstado(@NotNull Long estado) {
        this.estado = estado;
    }

    public @NotNull Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(@NotNull Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public @NotNull Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(@NotNull Long clienteId) {
        this.clienteId = clienteId;
    }
}
package com.api.crud.services.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL) // Excluir campos nulos durante la serialización
public class DetalleVentaDTO {
    @NotNull
    @JsonProperty(required = true)
    private Float horasVendidas;

    @NotNull
    @JsonProperty(required = true)
    private Float precioHora;

    @NotNull
    @JsonProperty(required = true)
    private String tipoServicio;

    public DetalleVentaDTO() {
    }

    public DetalleVentaDTO(Float horasVendidas, Float precioHora, String tipoServicio) {
        this.horasVendidas = horasVendidas;
        this.precioHora = precioHora;
        this.tipoServicio = tipoServicio;
    }

    public @NotNull Float getHorasVendidas() {
        return horasVendidas;
    }

    public void setHorasVendidas(@NotNull Float horasVendidas) {
        this.horasVendidas = horasVendidas;
    }

    public @NotNull Float getPrecioHora() {
        return precioHora;
    }

    public void setPrecioHora(@NotNull Float precioHora) {
        this.precioHora = precioHora;
    }

    public @NotNull String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(@NotNull String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }
}

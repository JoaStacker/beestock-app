package com.api.crud.services.models.dtos;

import com.api.crud.persistence.entities.Interaccion;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL) // Excluir campos nulos durante la serialización
public class InteraccionDTO {

    @NotNull
    @JsonProperty(required = true)
    private LocalDateTime fechaInteraccion;

    @NotNull
    @JsonProperty(required = true)
    private String medio;

    @NotNull
    @JsonProperty(required = true)
    private Long empleadoId;

    @NotNull
    @JsonProperty(required = true)
    private Long clienteId;

    public InteraccionDTO () {}

    public @NotNull LocalDateTime getFechaInteraccion() {
        return fechaInteraccion;
    }

    public void setFechaInteraccion(@NotNull LocalDateTime fechaInteraccion) {
        this.fechaInteraccion = fechaInteraccion;
    }

    public @NotNull String getMedio() {
        return medio;
    }

    public void setMedio(@NotNull String medio) {
        this.medio = medio;
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

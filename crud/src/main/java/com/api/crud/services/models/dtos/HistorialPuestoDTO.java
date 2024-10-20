package com.api.crud.services.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields during serialization
public class HistorialPuestoDTO {
    @NotNull
    private LocalDateTime fechaIngreso;

    @NotNull
    private Long empleadoId;

    @NotNull
    private Long puestoId;

    public @NotNull LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(@NotNull LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public @NotNull Long getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(@NotNull Long empleadoId) {
        this.empleadoId = empleadoId;
    }

    public @NotNull Long getPuestoId() {
        return puestoId;
    }

    public void setPuestoId(@NotNull Long puestoId) {
        this.puestoId = puestoId;
    }
}

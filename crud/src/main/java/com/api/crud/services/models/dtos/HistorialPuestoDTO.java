package com.api.crud.services.models.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Optional;

@JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields during serialization
public class HistorialPuestoDTO {

    @NotNull
    @JsonProperty(required = true)
    private LocalDateTime fechaIngreso;

    @NotNull
    @JsonProperty(required = true)
    private Long empleadoId;

    @NotNull
    @JsonProperty(required = true)
    private Long puestoId;

    public HistorialPuestoDTO() {
    }

    public HistorialPuestoDTO(LocalDateTime fechaIngreso, Long empleadoId, Long puestoId) {
        this.fechaIngreso = fechaIngreso;
        this.empleadoId = empleadoId;
        this.puestoId = puestoId;
    }

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

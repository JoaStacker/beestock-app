package com.api.crud.services.models.response.empleado;
import java.util.List;

public class EmpleadosResponseDTO {
    private List<EmpleadoResponseDTO> empleados;

    public EmpleadosResponseDTO() {
    }

    public EmpleadosResponseDTO(List<EmpleadoResponseDTO> emps) {
        this.empleados = emps;
    }

    public List<EmpleadoResponseDTO> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoResponseDTO> empleados) {
        this.empleados = empleados;
    }

}

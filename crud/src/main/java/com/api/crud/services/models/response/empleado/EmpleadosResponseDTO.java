package com.api.crud.services.models.response.empleado;
import com.api.crud.persistence.entities.Empleado;
import java.util.List;

public class EmpleadosResponseDTO {
    private List<Empleado> empleados;

    public EmpleadosResponseDTO() {
    }

    public EmpleadosResponseDTO(List<Empleado> emps) {
        this.empleados = emps;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

}

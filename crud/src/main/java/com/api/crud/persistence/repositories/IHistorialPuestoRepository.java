
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.HistorialPuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IHistorialPuestoRepository extends JpaRepository<HistorialPuesto, Long> {
    @Query(value="SELECT * FROM historial_puesto WHERE empleado_id= :empleado_id", nativeQuery = true)
    List<HistorialPuesto> findByEmpleadoId(Long empleado_id);

    @Query(value="SELECT * FROM historial_puesto WHERE empleado_id= :empleado_id ORDER BY fecha_ingreso DESC LIMIT 1", nativeQuery = true)
    HistorialPuesto findLastPuestoEmpleado(Long empleado_id);

}

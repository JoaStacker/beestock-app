
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {
    @Query(value="SELECT * FROM empleado WHERE borrado = false", nativeQuery = true)
    List<Empleado> findAllNotDeleted();

    @Query(value="SELECT * FROM empleado WHERE dni= :dni", nativeQuery = true)
    Optional<Empleado> findByDni(String dni);
}

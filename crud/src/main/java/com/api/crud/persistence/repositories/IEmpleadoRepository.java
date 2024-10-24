
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {
    @Query(value="SELECT * FROM empleado WHERE dni= :dni", nativeQuery = true)
    Optional<Empleado> findByDni(String dni);
}
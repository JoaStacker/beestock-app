
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Long> {
    // Original method with all three parameters
    @Query(value = "SELECT * FROM empleado WHERE dni LIKE %:searchstring% OR nombre LIKE %:searchstring% OR apellido LIKE %:searchstring%",
            nativeQuery = true)
    Optional<Empleado> findByFilter(String searchstring);

    @Query(value="SELECT * FROM empleado WHERE dni= :dni", nativeQuery = true)
    Optional<Empleado> findByDni(String dni);
}


package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Cliente;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    // Original method with all three parameters
    @Query(value = "SELECT * FROM cliente WHERE (cuit LIKE %:searchstring% OR nombre LIKE %:searchstring% OR apellido LIKE %:searchstring% OR email LIKE %:searchstring% OR MONTH(fecha_nacimiento) = :searchstring) AND borrado = false",
            nativeQuery = true)
    Optional<Cliente> findByFilter(String searchstring);

    @Query(value="SELECT * FROM cliente WHERE cuit= :cuit", nativeQuery = true)
    Optional<Cliente> findByCuit(String cuit);

    @Query(value = "SELECT * FROM cliente WHERE borrado = false",
            nativeQuery = true)
    List<Cliente> findAllNotDeleted();
}

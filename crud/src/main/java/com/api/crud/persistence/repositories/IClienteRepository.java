
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {
    // Original method with all three parameters
    @Query(value = "SELECT * FROM cliente WHERE cuit LIKE %:searchstring% OR nombre LIKE %:searchstring% OR apellido LIKE %:searchstring% OR email LIKE %:searchstring%",
            nativeQuery = true)
    Optional<Cliente> findByFilter(String searchstring);

    @Query(value="SELECT * FROM cliente WHERE cuit= :cuit", nativeQuery = true)
    Optional<Cliente> findByCuit(String cuit);
}

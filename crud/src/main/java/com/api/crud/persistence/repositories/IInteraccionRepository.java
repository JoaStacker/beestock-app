package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Cliente;
import com.api.crud.persistence.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IInteraccionRepository extends JpaRepository<Cliente, Long> {
    @Query(value="SELECT * FROM cliente WHERE id= :id", nativeQuery = true)
    Optional<Cliente> findById(Integer id);
}

package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Cliente;
import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.Interaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IInteraccionRepository extends JpaRepository<Interaccion, Long> {
    @Query(value="SELECT * FROM interaccion WHERE cliente_id= :cliente_id", nativeQuery = true)
    List<Interaccion> findByCliente(@Param("cliente_id") Long id);
}

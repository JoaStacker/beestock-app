
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Empleado;
import com.api.crud.persistence.entities.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPuestoRepository extends JpaRepository<Puesto, Long> {
}

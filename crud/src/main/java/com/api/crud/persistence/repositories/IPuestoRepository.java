
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Puesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPuestoRepository extends JpaRepository<Puesto, Long> {
}

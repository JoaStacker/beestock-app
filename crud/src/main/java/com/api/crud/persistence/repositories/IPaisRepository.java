
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Pais;
import com.api.crud.persistence.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPaisRepository extends JpaRepository<Pais, Long> {
}

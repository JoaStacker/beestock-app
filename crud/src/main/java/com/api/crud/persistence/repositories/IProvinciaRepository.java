
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Provincia;
import com.api.crud.persistence.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProvinciaRepository extends JpaRepository<Provincia, Long> {
    @Query(value="SELECT * FROM provincia WHERE pais_id= :pais_id", nativeQuery = true)
    List<Provincia> findAllByPais(Long pais_id);
}

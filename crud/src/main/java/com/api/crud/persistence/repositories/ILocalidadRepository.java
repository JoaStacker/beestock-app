package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Localidad;
import com.api.crud.persistence.entities.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILocalidadRepository extends JpaRepository<Localidad, Long> {
    @Query(value="SELECT * FROM localidad WHERE provincia_id= :provincia_id", nativeQuery = true)
    List<Localidad> findAllByProvincia(Long provincia_id);
}

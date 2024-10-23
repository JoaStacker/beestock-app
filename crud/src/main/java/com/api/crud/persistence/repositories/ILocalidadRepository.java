package com.api.crud.persistence.repositories;


import com.api.crud.persistence.entities.Localidad;
import com.api.crud.persistence.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILocalidadRepository extends JpaRepository<Localidad, Long> {
}

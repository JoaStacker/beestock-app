package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query(value="SELECT * FROM proveedor WHERE cuit= :cuit", nativeQuery = true)
    Optional<Proveedor> findByCuit(String cuit);

    Optional<Proveedor> findById(Long id);




}

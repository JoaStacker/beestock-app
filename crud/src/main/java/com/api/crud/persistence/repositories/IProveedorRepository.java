package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Proveedor;
import com.api.crud.services.models.response.proveedor.ProveedorResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor, Long> {
    @Query("SELECT p FROM Proveedor p LEFT JOIN FETCH p.incidentes WHERE p.borrado = false")
    List<Proveedor> findAllWithIncidentes();

    @Query(value="SELECT * FROM proveedor WHERE borrado = false", nativeQuery = true)
    List<Proveedor> findAllNotDeleted();

    @Query(value="SELECT * FROM proveedor WHERE cuit= :cuit", nativeQuery = true)
    Optional<Proveedor> findByCuit(@Param("cuit") String cuit);

    Optional<Proveedor> findById(Long id);




}

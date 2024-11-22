package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Cliente;
import com.api.crud.persistence.entities.Incidente;
import com.api.crud.persistence.entities.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IIncidenteRepository extends JpaRepository<Incidente, Long> {
    @Query(value="SELECT * FROM incidente WHERE estado = '1' AND proveedor_id = :prov_id", nativeQuery = true)
    List<Incidente> getAllPendingByProveedor(Long prov_id);
}

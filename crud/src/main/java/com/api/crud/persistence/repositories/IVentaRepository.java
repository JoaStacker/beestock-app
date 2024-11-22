
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
    @Query(value="SELECT * FROM venta WHERE cliente_id= :cliente_id ORDER BY fecha_venta", nativeQuery = true)
    List<Venta> findAllByClienteId(@Param("cliente_id") Long cliente_id);
}

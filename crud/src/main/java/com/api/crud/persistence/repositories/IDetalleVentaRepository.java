
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.DetalleVenta;
import com.api.crud.persistence.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
    @Query(value="SELECT * FROM detalle_venta WHERE venta_id= :venta_id", nativeQuery = true)
    List<DetalleVenta> findByVentaId(Long venta_id);
}

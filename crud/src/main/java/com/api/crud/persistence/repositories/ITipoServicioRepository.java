package com.api.crud.persistence.repositories;


import com.api.crud.persistence.entities.HistorialPuesto;
import com.api.crud.persistence.entities.Proveedor;
import com.api.crud.persistence.entities.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITipoServicioRepository extends JpaRepository<TipoServicio, Long> {
    @Query(value = "SELECT * FROM tipo_servicio WHERE id IN :lista_ids", nativeQuery = true)
    List<TipoServicio> findByFilter(@Param("lista_ids") List<Long> listaIds);
}

package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.Incidente;
import com.api.crud.persistence.entities.TipoServicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IIncidenteRepository extends JpaRepository<Incidente, Long> {
}

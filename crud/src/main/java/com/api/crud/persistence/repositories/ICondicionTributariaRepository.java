package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.CondicionTributaria;
import com.api.crud.persistence.entities.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICondicionTributariaRepository extends JpaRepository<CondicionTributaria, Long> {
}

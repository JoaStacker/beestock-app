package com.api.crud.persistence.repositories;


import com.api.crud.persistence.entities.Proveedor;
import com.api.crud.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProveedorRepository extends JpaRepository<Proveedor, Long> {
}

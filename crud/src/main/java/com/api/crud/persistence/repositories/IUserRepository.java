
package com.api.crud.persistence.repositories;

import com.api.crud.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    @Query(value="SELECT * FROM user WHERE email= :email", nativeQuery = true)
    Optional<UserEntity> findByEmail(String email);

}

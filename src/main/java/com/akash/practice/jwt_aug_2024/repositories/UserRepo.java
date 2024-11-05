package com.akash.practice.jwt_aug_2024.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.practice.jwt_aug_2024.models.HumanBeing;

@Repository
public interface UserRepo extends JpaRepository<HumanBeing, Integer> {
    Optional<HumanBeing> findById(int id);

    Optional<HumanBeing> findByUniqueId(int uniqueId);

    Optional<HumanBeing> findByEmail(String email);

    boolean existsByUniqueId(int id);

    boolean existsByEmail(String email);

    void deleteAllByEmail(String email);

    // Integer getUniqueIdFromEmail(String email);

}

package com.akash.practice.jwt_aug_2024.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.akash.practice.jwt_aug_2024.models.MedicalDoc;

@Repository
public interface MedicalDocRepo extends JpaRepository<MedicalDoc, Integer> {
    boolean existsByUniqueId(int id);

    Optional<MedicalDoc> findById(int id);

    Optional<MedicalDoc> findByUniqueId(int id);

}

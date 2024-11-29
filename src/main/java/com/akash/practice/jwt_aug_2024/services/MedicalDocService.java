package com.akash.practice.jwt_aug_2024.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.practice.jwt_aug_2024.models.HumanBeing;
import com.akash.practice.jwt_aug_2024.models.MedicalDoc;
import com.akash.practice.jwt_aug_2024.repositories.MedicalDocRepo;
import com.akash.practice.jwt_aug_2024.repositories.UserRepo;
import com.akash.practice.jwt_aug_2024.utilities.UniqueId;

// @PropertySource("classpath:application-dev.properties")
@Service
public class MedicalDocService {

    // @Autowired

    // @Autowired
    MedicalDocRepo medicalDocRepo;
    @Value("${serverLocation}")
    String serverLocation;

    UserRepo userRepo;

    public MedicalDocService(MedicalDocRepo medicalDocRepo, UserRepo userRepo) {
        this.medicalDocRepo = medicalDocRepo;
        this.userRepo = userRepo;
    }

    public ResponseEntity<Object> findById(int docId) {
        // System.out.println(userService.getUserByRole("MANAGER"));
        // System.out.println(medicalDocs.get(docId));
        if (medicalDocRepo.existsByUniqueId(docId)) {
            // return medicalDocRepo.findById(docId).get();
            return ResponseEntity
                    .ok(medicalDocRepo.findByUniqueId(docId).get().toString() + "\nservers are located in "
                            + serverLocation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Medical document with id " + docId + " is not available." + "\nservers are located in "
                            + serverLocation);
        }
        // return null;
    }

    public ResponseEntity<Object> addMedicalDoc(MedicalDoc medicalDoc) {
        int uniqueId = UniqueId.getNextMedicalReportCount();
        // SEE need to change this possible infinite loop
        while (medicalDocRepo.existsByUniqueId(uniqueId)) {
            // uniqueId = new Random().nextInt(1000000000);
            uniqueId = UniqueId.getNextMedicalReportCount();
        }
        medicalDoc.setUniqueId(uniqueId);
        if (userRepo.existsByUniqueId(medicalDoc.getPatientId())) {
            return ResponseEntity.ok(medicalDocRepo.save(medicalDoc));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Patient ID " + medicalDoc.getPatientId() + " doest not exists.");
        }

        /*
         * private int uniqueId;
         * private int doctorId;
         * private int patientId;
         * private double billAmount;
         * private String doctorComments;
         * private String checkUpDate;
         */

        // if (medicalDocRepo.existsByUniqueId(uniqueId)) {
        // return ResponseEntity.badRequest().body(null);
        // } else {
        // medicalDoc.setUniqueId(uniqueId);
        // return ResponseEntity.ok(medicalDocRepo.save(medicalDoc));
        // }
    }

    public ResponseEntity<Object> editMedicalDoc(MedicalDoc medicalDoc) {
        System.out.println("doc id : " + medicalDoc.getUniqueId());
        if (medicalDocRepo.existsByUniqueId(medicalDoc.getUniqueId())) {
            MedicalDoc tempDoc = medicalDocRepo.findByUniqueId(medicalDoc.getUniqueId()).get();
            boolean isDoctorIdNotSame = medicalDoc.getDoctorId() != tempDoc.getDoctorId();
            boolean isPatientIdNotSame = medicalDoc.getPatientId() != tempDoc.getPatientId();
            if (tempDoc.equals(medicalDoc)) {
                return ResponseEntity.ok("Data is same !");
            } else if (!userRepo.existsByUniqueId(medicalDoc.getPatientId())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Patient ID " + medicalDoc.getPatientId() + " doest not exists.");
            } else if (isDoctorIdNotSame) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Doctor Id must need be same");
            } else if (isPatientIdNotSame) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patient Id must need be same");
            } else {
                System.out.println(tempDoc.getMatchingString(tempDoc, medicalDoc));
                tempDoc.setValues(medicalDoc);
                return ResponseEntity.status(HttpStatus.OK).body(medicalDocRepo.save(tempDoc));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Medical document with id " + medicalDoc.getUniqueId() + " is not available.");
        }
    }

    public ResponseEntity<Object> deleteMedicalDoc(int id) {
        Optional<MedicalDoc> document = medicalDocRepo.findByUniqueId(id);
        if (document.isPresent()) {
            int creator = document.get().getDoctorId();
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.isAuthenticated()) {
                HumanBeing current = userRepo.findByEmail(auth.getPrincipal().toString()).get();
                if (creator == current.getUniqueId()) {
                    medicalDocRepo.deleteById(id);
                    return ResponseEntity.status(HttpStatus.OK).body("document deleted successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You are not creator of this document");
                }
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("user not authenticated");
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found");
        }
    }

}

package com.akash.practice.jwt_aug_2024.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akash.practice.jwt_aug_2024.models.MedicalDoc;
import com.akash.practice.jwt_aug_2024.services.MedicalDocService;
import com.akash.practice.jwt_aug_2024.utilities.MyProperties;

@RestController
@RequestMapping("/documents")
public class MedicalDocController {

    MedicalDocService medicalDocService;

    @Autowired
    MyProperties myProperties;

    public MedicalDocController(MedicalDocService medicalDocService) {
        this.medicalDocService = medicalDocService;
    }

    @GetMapping("/{docId}")
    ResponseEntity<Object> getMedicalDocument(@PathVariable int docId) {
        // System.out.println("from app.prop : " + myProperties.getName());
        // myProperties.setName("Akash H Vyas");
        // System.out.println("from app.prop : " + myProperties.getName());
        // System.out.println("from app.prop : " + myProperties.getEstablishYear());
        // System.out.println("from app.prop : " + myProperties.getPartners());
        return medicalDocService.findById(docId);
    }

    @PostMapping
    ResponseEntity<Object> addMedicalDoc(@RequestBody MedicalDoc medicalDoc) {
        return medicalDocService.addMedicalDoc(medicalDoc);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteMedicalDoc(@PathVariable int id) {
        return medicalDocService.deleteMedicalDoc(id);
    }

    @PutMapping
    ResponseEntity<Object> editMedicalDoc(@RequestBody MedicalDoc medicalDoc) {
        return medicalDocService.editMedicalDoc(medicalDoc);
    }

}

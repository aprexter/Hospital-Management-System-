package com.aprexter.hospitalmanagementsystem.service;

import com.aprexter.hospitalmanagementsystem.models.Insurance;
import com.aprexter.hospitalmanagementsystem.models.Patient;
import com.aprexter.hospitalmanagementsystem.repositry.InsuranceRepositry;
import com.aprexter.hospitalmanagementsystem.repositry.PatientRepositry;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class InsuranceService {
    private final InsuranceRepositry insuranceRepository;
    private final PatientRepositry patientRepository;

    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        patient.setInsurance(insurance);
        insurance.setPatient(patient); // bidirectional consistency maintainence

        return patient;
    }

    @Transactional
    public Patient disaccociateInsuranceFromPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        patient.setInsurance(null);
        return patient;
    }

    // HW
    //Create three appointment for a patient and then delete Patient
}

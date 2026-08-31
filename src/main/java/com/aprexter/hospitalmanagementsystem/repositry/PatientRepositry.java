package com.aprexter.hospitalmanagementsystem.repositry;

import com.aprexter.hospitalmanagementsystem.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepositry extends JpaRepository<Patient,Long> {
}

package com.aprexter.hospitalmanagementsystem.repositry;

import com.aprexter.hospitalmanagementsystem.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepositry extends JpaRepository<Doctor,Long> {
}

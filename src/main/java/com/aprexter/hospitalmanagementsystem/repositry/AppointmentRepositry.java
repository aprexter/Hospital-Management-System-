package com.aprexter.hospitalmanagementsystem.repositry;

import com.aprexter.hospitalmanagementsystem.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepositry extends JpaRepository<Appointment, Long> {
}

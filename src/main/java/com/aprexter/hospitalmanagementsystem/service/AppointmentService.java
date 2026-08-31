package com.aprexter.hospitalmanagementsystem.service;

import com.aprexter.hospitalmanagementsystem.dto.AppointmentRequestDto;
import com.aprexter.hospitalmanagementsystem.dto.AppointmentResponseDto;
import com.aprexter.hospitalmanagementsystem.models.Appointment;
import com.aprexter.hospitalmanagementsystem.models.Doctor;
import com.aprexter.hospitalmanagementsystem.models.Patient;
import com.aprexter.hospitalmanagementsystem.repositry.AppointmentRepositry;
import com.aprexter.hospitalmanagementsystem.repositry.DoctorRepositry;
import com.aprexter.hospitalmanagementsystem.repositry.PatientRepositry;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AppointmentService {

    private final AppointmentRepositry appointmentRepository;
    private final DoctorRepositry doctorRepository;
    private final PatientRepositry patientRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Secured("ROLE_PATIENT")
    public AppointmentResponseDto createNewAppointment(AppointmentRequestDto appointmentRequestDto) {
        Long doctorId = appointmentRequestDto.getDoctorId();
        Long patientId = appointmentRequestDto.getPatientId();

        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + patientId));
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + doctorId));
        Appointment appointment = Appointment.builder()
                .reason(createAppointmentRequestDto.getReason())
                .appointmentTime(createAppointmentRequestDto.getAppointmentTime())
                .build();

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        patient.getAppointments().add(appointment); // to maintain consistency

        appointment = appointmentRepository.save(appointment);
        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @Transactional
    @PreAuthorize("hasAuthority('appointment:write') or #doctorId == authentication.principal.id")
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor); // this will automatically call the update, because it is dirty

        doctor.getAppointments().add(appointment);

        //UsernamePasswordAuthenticationToken
        // just for bidirectional consistency

        return appointment;
    }
    @PreAuthorize("hasRole('ADMIN') OR (hasRole('DOCTOR') AND #doctorId == authentication.principal.id)")
    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }
}

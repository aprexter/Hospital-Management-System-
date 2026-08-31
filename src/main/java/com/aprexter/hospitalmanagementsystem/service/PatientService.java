package com.aprexter.hospitalmanagementsystem.service;

import com.aprexter.hospitalmanagementsystem.dto.PatientResponseDto;
import com.aprexter.hospitalmanagementsystem.models.Patient;
import com.aprexter.hospitalmanagementsystem.repositry.PatientRepositry;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.boot.Banner;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PatientService {

    private final PatientRepositry  patientRepositry;
    private final ModelMapper modelMapper;

    public List<PatientResponseDto> getAllPatients(Integer pageNumber, Integer pageSize) {
        return patientRepositry.findAll(PageRequest.of(pageNumber,pageSize))
                .stream()
                .map(patient -> modelMapper.map(patient,PatientResponseDto.class))
                .collect(Collectors.toList());
    }
    @Transactional
    public PatientResponseDto getPatientById(Long patientId) {
        Patient patient = patientRepositry.findById(patientId).orElseThrow(() -> new EntityNotFoundException("Patient Not " +
                "Found with id: " + patientId));
        return modelMapper.map(patient, PatientResponseDto.class);
    }
}

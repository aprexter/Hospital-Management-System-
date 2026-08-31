package com.aprexter.hospitalmanagementsystem.service;

import com.aprexter.hospitalmanagementsystem.dto.DoctorResponseDto;
import com.aprexter.hospitalmanagementsystem.dto.OnboardDoctorRequestDto;
import com.aprexter.hospitalmanagementsystem.models.Doctor;
import com.aprexter.hospitalmanagementsystem.models.User;
import com.aprexter.hospitalmanagementsystem.models.type.RoleType;
import com.aprexter.hospitalmanagementsystem.repositry.DoctorRepositry;
import com.aprexter.hospitalmanagementsystem.repositry.UserRepositry;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class DoctorService {
    private final DoctorRepositry doctorRepository;
    private final ModelMapper modelMapper;
    private final UserRepositry userRepository;

    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }


    @Transactional
    public DoctorResponseDto onBoardNewDoctor(OnboardDoctorRequestDto onBoardDoctorRequestDto) {
        User user = userRepository.findById(onBoardDoctorRequestDto.getUserId()).orElseThrow();

        if(doctorRepository.existsById(onBoardDoctorRequestDto.getUserId())) {
            throw new IllegalArgumentException("Already a doctor");
        }

        Doctor doctor = Doctor.builder()
                .name(onBoardDoctorRequestDto.getName())
                .specialization(onBoardDoctorRequestDto.getSpecialization())
                .user(user)
                .build();

        user.getRoles().add(RoleType.DOCTOR);

        return modelMapper.map(doctorRepository.save(doctor), DoctorResponseDto.class);
    }
}

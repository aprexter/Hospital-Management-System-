package com.aprexter.hospitalmanagementsystem.controller;

import com.aprexter.hospitalmanagementsystem.dto.DoctorResponseDto;
import com.aprexter.hospitalmanagementsystem.dto.OnboardDoctorRequestDto;
import com.aprexter.hospitalmanagementsystem.dto.PatientResponseDto;
import com.aprexter.hospitalmanagementsystem.service.DoctorService;
import com.aprexter.hospitalmanagementsystem.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final PatientService patientService;
    private final DoctorService  doctorService;

    @GetMapping()
    public ResponseEntity<List<PatientResponseDto>> getPatientById(@RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
                                                                  @RequestParam(value = "size", defaultValue = "10") Integer pageSize){
        return ResponseEntity.ok(patientService.getAllPatients(pageNumber, pageSize));

    }
    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(@RequestBody OnboardDoctorRequestDto onboardDoctorRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(doctorService.onBoardNewDoctor(onboardDoctorRequestDto));
    }


}

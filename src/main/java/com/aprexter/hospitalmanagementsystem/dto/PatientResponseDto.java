package com.aprexter.hospitalmanagementsystem.dto;

import com.aprexter.hospitalmanagementsystem.models.type.BloodGrpType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private BloodGrpType bloodGroup;
}

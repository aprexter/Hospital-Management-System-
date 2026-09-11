package com.aprexter.hospitalmanagementsystem.models;

import com.aprexter.hospitalmanagementsystem.models.type.BloodGrpType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Table(
        name = "patient",
        uniqueConstraints = {
               //@UniqueConstraint(name = "unique_patient_email", columnNames = {"email"}), // alredy done on column level
                @UniqueConstraint(name = "unique_patient_name_birthdate", columnNames = {"name", "birthDate"})
        },
        indexes = {
                @Index(name = "idx_birth_date", columnList = "birthDate")
        }
)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient extends BaseModel {
    @Column(nullable = false, length = 40)
    private String name;
    @Column(nullable = false, length = 40,name = "birth_date")
    private LocalDate birthDate;

    @Column(unique = true, nullable = false)
    private String email;

    private String gender;

    @OneToOne
    @MapsId //Use the primary key of the associated User as the primary key of Patient
    /**
     * The patient.id itself acts as both:
     *
     * Primary Key of patient
     * Foreign Key referencing user.id
     */
    private User user;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private BloodGrpType bloodGroup;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    /**
     * If the Insurance is removed from the Patient's relationship,not the patient itself, JPA should delete that Insurance record from the database.
     * Patient owns an Insurance record.this ,this Manage the Insurance lifecycle together with the Patient.
     */
    @JoinColumn(name = "pat_ins_id")
    private Insurance insurance;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Appointment> appointments = new ArrayList<>();
}

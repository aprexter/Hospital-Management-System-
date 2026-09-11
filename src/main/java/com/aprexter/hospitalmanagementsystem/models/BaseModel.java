package com.aprexter.hospitalmanagementsystem.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)  need to do for the Spring  data Jpa auditng
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@CreatedDate is also a good choice, but it belongs to Spring Data JPA auditing
    //need to enable  Jpa auditing
    @CreationTimestamp // Hibernate specific annotaion
    @Column(name = "created_at",nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp// Hibernate specific annotaion for
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;
}

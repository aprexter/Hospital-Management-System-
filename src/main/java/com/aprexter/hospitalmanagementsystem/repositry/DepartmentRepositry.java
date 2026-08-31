package com.aprexter.hospitalmanagementsystem.repositry;

import com.aprexter.hospitalmanagementsystem.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepositry extends JpaRepository<Department,Long> {
}

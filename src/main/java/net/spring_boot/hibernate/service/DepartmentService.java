package net.spring_boot.hibernate.service;

import net.spring_boot.hibernate.entity.Department;
import net.spring_boot.hibernate.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // Only for Query Demonstrations
    public Department getDepartmentByName(String name) {
        return departmentRepository.findByName(name).orElse(null);
    }

    // Only for Query Demonstrations
    public List<Department> getDepartmentsWithHeadDoctor() {
        return departmentRepository.findByHeadDoctorIsNotNull();
    }

    public List<Department> getDepartmentsWithDoctors() {
        return departmentRepository.findAllWithDoctors();
    }

    // Only for Query Demonstrations
    public List<Department> getDepartmentsWithMoreThanDoctors(int minDoctors) {
        return departmentRepository.findDepartmentsWithMoreThanDoctors(minDoctors);
    }

    // Only for Query Demonstrations
    public List<Department> getDepartmentsByHeadDoctorName(String doctorName) {
        return departmentRepository.findByHeadDoctorName(doctorName);
    }
}

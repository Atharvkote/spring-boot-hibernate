package net.spring_boot.hibernate.repository;

import net.spring_boot.hibernate.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Finds a department by its name.
    // Returns Optional.empty() if no department exists.
    Optional<Department> findByName(String name);

    // Returns all departments that have
    // an assigned head doctor.
    List<Department> findByHeadDoctorIsNotNull();

    // Fetches all departments together with
    // their associated doctors in a single query.
    // DISTINCT prevents duplicate departments
    // when multiple doctors belong to the same department.
    @Query("""
            SELECT DISTINCT department
            FROM Department department
            JOIN FETCH department.doctors
            """)
    List<Department> findAllWithDoctors();

    // Returns departments having more than
    // the specified number of doctors.
    // SIZE() counts the elements in the doctors collection.
    @Query("""
            SELECT department
            FROM Department department
            WHERE SIZE(department.doctors) > :minDoctors
            """)
    List<Department> findDepartmentsWithMoreThanDoctors(
            @Param("minDoctors") int minDoctors
    );

    // Returns departments whose head doctor
    // has the specified name.
    @Query("""
            SELECT department
            FROM Department department
            WHERE department.headDoctor.name = :doctorName
            """)
    List<Department> findByHeadDoctorName(
            @Param("doctorName") String doctorName
    );
}
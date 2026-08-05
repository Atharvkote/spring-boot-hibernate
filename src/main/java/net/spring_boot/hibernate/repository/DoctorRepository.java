package net.spring_boot.hibernate.repository;

import net.spring_boot.hibernate.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    // Returns all doctors with the specified specialization.
    List<Doctor> findBySpecialization(String specialization);

    // Searches doctors whose names contain
    // the given keyword.
    List<Doctor> findByNameContaining(String name);

    // Retrieves doctors who have at least
    // one appointment assigned.
    // SIZE() returns the number of appointments.
    @Query("""
            SELECT doctor
            FROM Doctor doctor
            WHERE SIZE(doctor.appointments) > 0
            """)
    List<Doctor> findDoctorsWithAppointments();

    // Retrieves doctors belonging to
    // a specific department.
    @Query("""
            SELECT doctor
            FROM Doctor doctor
            JOIN doctor.departments department
            WHERE department.name = :departmentName
            """)
    List<Doctor> findByDepartmentName(
            @Param("departmentName") String departmentName
    );

    // Fetches all doctors together with
    // their appointments in a single query.
    // DISTINCT avoids duplicate doctors when
    // multiple appointments exist.
    @Query("""
            SELECT DISTINCT doctor
            FROM Doctor doctor
            LEFT JOIN FETCH doctor.appointments
            """)
    List<Doctor> findAllWithAppointments();

    // Returns doctors whose appointment count
    // is greater than the specified value.
    @Query("""
            SELECT doctor
            FROM Doctor doctor
            WHERE SIZE(doctor.appointments) > :minAppointments
            """)
    List<Doctor> findDoctorsWithMoreThanAppointments(
            @Param("minAppointments") int minAppointments
    );
}
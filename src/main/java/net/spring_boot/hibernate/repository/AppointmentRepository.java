package net.spring_boot.hibernate.repository;

import net.spring_boot.hibernate.entity.Appointment;
import net.spring_boot.hibernate.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for Appointment entity.
 * Provides CRUD operations and custom queries
 * related to appointments, doctors, and patients.
 */
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Returns appointments whose reason
    // contains the specified keyword.
    List<Appointment> findByReasonContaining(String reason);

    // Returns appointments scheduled
    // between the given start and end date-time.
    List<Appointment> findByAppointmentTimeBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    // Returns all appointments assigned
    // to the specified doctor.
    List<Appointment> findByDoctorId(Long doctorId);

    // Returns all appointments belonging
    // to the specified patient.
    List<Appointment> findByPatientId(Long patientId);

    // Custom JPQL query to retrieve all
    // appointments for the given Doctor entity.
    @Query("""
            SELECT appointment
            FROM Appointment appointment
            WHERE appointment.doctor = :doctor
            """)
    List<Appointment> findByDoctor(
            @Param("doctor") Doctor doctor
    );

    // Fetches appointments together with
    // their associated patient to avoid
    // the N+1 query problem.
    @Query("""
            SELECT appointment
            FROM Appointment appointment
            JOIN FETCH appointment.patient
            WHERE appointment.doctor.id = :doctorId
            """)
    List<Appointment> findAllWithPatientByDoctorId(
            @Param("doctorId") Long doctorId
    );

    // Counts the total number of appointments
    // assigned to a specific doctor.
    @Query("""
            SELECT COUNT(appointment)
            FROM Appointment appointment
            WHERE appointment.doctor.id = :doctorId
            """)
    long countByDoctorId(
            @Param("doctorId") Long doctorId
    );
}
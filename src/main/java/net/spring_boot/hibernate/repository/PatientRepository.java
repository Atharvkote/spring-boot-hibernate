package net.spring_boot.hibernate.repository;

import jakarta.transaction.Transactional;
import net.spring_boot.hibernate.dto.ops.BloodGroupCountResponseEntity;
import net.spring_boot.hibernate.entity.Patient;
import net.spring_boot.hibernate.enums.BloodGroupType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface PatientRepository extends JpaRepository<Patient, Long> {

    // Finds a patient by name.
    // Returns Optional.empty() if no matching patient exists.
    Optional<Patient> findByName(String name);

    // Finds patients whose birthdate matches OR email matches.
    List<Patient> findByBirthDateOrEmail(LocalDate birthDate, String email);

    // Finds all patients born between the given dates.
    List<Patient> findByBirthDateBetween(LocalDate startDate, LocalDate endDate);

    // Performs a LIKE search on patient name and
    // returns the results ordered by ID in descending order.
    List<Patient> findByNameContainingOrderByIdDesc(String query);

    // Custom JPQL query to retrieve patients
    // belonging to a specific blood group.
    @Query("""
            SELECT record
            FROM Patient record
            WHERE record.bloodGroup = :bloodGroup
            """)
    List<Patient> findByBloodGroup(@Param("bloodGroup") BloodGroupType bloodGroup);

    // Custom JPQL query to retrieve all patients
    // born after the specified date.
    @Query("""
            SELECT record
            FROM Patient record
            WHERE record.birthDate > :birthDate
            """)
    List<Patient> findByBornAfterDate(@Param("birthDate") LocalDate birthDate);

    // JPQL constructor expression.
    // Groups patients by blood group and returns
    // the count for each blood group as a DTO.
    @Query("""
            SELECT NEW net.spring_boot.hibernate.dto.BloodGroupCountResponseEntity(
                record.bloodGroup,
                COUNT(record)
            )
            FROM Patient record
            GROUP BY record.bloodGroup
            """)
    List<BloodGroupCountResponseEntity> countEachBloodGroupType();

    // Native SQL query with pagination support.
    // Returns a Page of patients.
    @Query(
            value = """
                    SELECT *
                    FROM patient
                    """,
            nativeQuery = true
    )
    Page<Patient> findAllPatients(Pageable pageable);

    // Executes a JPQL UPDATE query.
    // @Modifying indicates this query modifies data.
    // Returns the number of rows updated.
    @Transactional
    @Modifying
    @Query("""
            UPDATE Patient record
            SET record.name = :name
            WHERE record.id = :id
            """)
    int updateNameWithId(
            @Param("name") String name,
            @Param("id") Long id
    );

    // Uses FETCH JOIN to eagerly load appointments
    // along with patients in a single query.
    // DISTINCT prevents duplicate patients when
    // multiple appointments exist.
    @Query("""
            SELECT DISTINCT record
            FROM Patient record
            LEFT JOIN FETCH record.appointments
            """)
    List<Patient> findAllPatientWithAppointment();
}
package net.spring_boot.hibernate.repository;

import net.spring_boot.hibernate.entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {

    // Finds an insurance policy using its unique policy number.
    // Returns Optional.empty() if no matching policy exists.
    Optional<Insurance> findByPolicyNumber(String policyNumber);

    // Returns all insurance policies
    // provided by the specified insurance company.
    List<Insurance> findByProvider(String provider);

    // Returns all policies that remain valid
    // after the given date.
    List<Insurance> findByValidUntilAfter(LocalDate date);

    // Returns all policies that expire
    // before the given date.
    List<Insurance> findByValidUntilBefore(LocalDate date);

    // Custom JPQL query to retrieve
    // all currently valid insurance policies.
    @Query("""
            SELECT insurance
            FROM Insurance insurance
            WHERE insurance.validUntil > :date
            """)
    List<Insurance> findValidInsurances(
            @Param("date") LocalDate date
    );

    // Custom JPQL query to retrieve
    // all expired insurance policies.
    @Query("""
            SELECT insurance
            FROM Insurance insurance
            WHERE insurance.validUntil < :date
            """)
    List<Insurance> findExpiredInsurances(
            @Param("date") LocalDate date
    );

    // Fetches all insurance policies together
    // with their associated patient using
    // FETCH JOIN to avoid the N+1 query problem.
    @Query("""
            SELECT insurance
            FROM Insurance insurance
            JOIN FETCH insurance.patient
            """)
    List<Insurance> findAllWithPatient();
}
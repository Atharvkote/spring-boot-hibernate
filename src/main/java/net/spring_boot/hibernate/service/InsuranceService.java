package net.spring_boot.hibernate.service;

import net.spring_boot.hibernate.entity.Insurance;
import net.spring_boot.hibernate.entity.Patient;
import net.spring_boot.hibernate.repository.InsuranceRepository;
import net.spring_boot.hibernate.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InsuranceService {

    private final InsuranceRepository insuranceRepository;
    private final PatientRepository patientRepository;

    // Only for Query Demonstrations
    @Transactional
    public Patient assignInsuranceToPatient(Insurance insurance, Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        patient.setInsurance(insurance);
        insurance.setPatient(patient);

        return patient;
    }

    // Only for Query Demonstrations
    @Transactional
    public Patient disaccociateInsuranceFromPatient(Long patientId) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));

        patient.setInsurance(null);
        return patient;
    }

    // Only for Query Demonstrations
    public Insurance getInsuranceByPolicyNumber(String policyNumber) {
        return insuranceRepository.findByPolicyNumber(policyNumber).orElse(null);
    }

    // Only for Query Demonstrations
    public List<Insurance> getInsurancesByProvider(String provider) {
        return insuranceRepository.findByProvider(provider);
    }
    // Only for Query Demonstrations
    public List<Insurance> getValidInsurances(LocalDate date) {
        return insuranceRepository.findValidInsurances(date);
    }

    // Only for Query Demonstrations
    public List<Insurance> getExpiredInsurances(LocalDate date) {
        return insuranceRepository.findExpiredInsurances(date);
    }

    // Only for Query Demonstrations
    public List<Insurance> getInsurancesWithPatient() {
        return insuranceRepository.findAllWithPatient();
    }
}

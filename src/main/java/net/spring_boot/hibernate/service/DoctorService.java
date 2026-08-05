package net.spring_boot.hibernate.service;

import net.spring_boot.hibernate.dto.ops.DoctorResponseDto;
import net.spring_boot.hibernate.dto.ops.OnboardDoctorRequestDto;
import net.spring_boot.hibernate.entity.Doctor;
import net.spring_boot.hibernate.entity.User;
import net.spring_boot.hibernate.enums.RoleType;
import net.spring_boot.hibernate.repository.DoctorRepository;
import net.spring_boot.hibernate.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public List<DoctorResponseDto> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public DoctorResponseDto onBoardNewDoctor(OnboardDoctorRequestDto onBoardDoctorRequestDto) {
        User user = userRepository.findById(onBoardDoctorRequestDto.getUserId()).orElseThrow();

        if(doctorRepository.existsById(onBoardDoctorRequestDto.getUserId())) {
            throw new IllegalArgumentException("Already a doctor");
        }

        Doctor doctor = Doctor.builder()
                .name(onBoardDoctorRequestDto.getName())
                .specialization(onBoardDoctorRequestDto.getSpecialization())
                .user(user)
                .build();

        user.getRoles().add(RoleType.DOCTOR);

        return modelMapper.map(doctorRepository.save(doctor), DoctorResponseDto.class);
    }

    public List<DoctorResponseDto> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization)
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    // Only for Query Demonstrations
    public List<DoctorResponseDto> getDoctorsByName(String name) {
        return doctorRepository.findByNameContaining(name)
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    // Only for Query Demonstrations
    public List<DoctorResponseDto> getDoctorsByDepartmentName(String departmentName) {
        return doctorRepository.findByDepartmentName(departmentName)
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    // Only for Query Demonstrations
    public List<DoctorResponseDto> getDoctorsWithAppointments() {
        return doctorRepository.findDoctorsWithAppointments()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    // Only for Query Demonstrations
    public List<DoctorResponseDto> getDoctorsWithMoreThanAppointments(int minAppointments) {
        return doctorRepository.findDoctorsWithMoreThanAppointments(minAppointments)
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }

    // Only for Query Demonstrations
    public List<DoctorResponseDto> getAllDoctorsWithAppointments() {
        return doctorRepository.findAllWithAppointments()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorResponseDto.class))
                .collect(Collectors.toList());
    }
}

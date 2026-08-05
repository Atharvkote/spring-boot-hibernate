package net.spring_boot.hibernate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.spring_boot.hibernate.dto.ops.AppointmentResponseDto;
import net.spring_boot.hibernate.dto.ops.CreateAppointmentRequestDto;
import net.spring_boot.hibernate.dto.ops.PatientResponseDto;
import net.spring_boot.hibernate.entity.Appointment;
import net.spring_boot.hibernate.service.AppointmentService;
import net.spring_boot.hibernate.service.PatientService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
@Tag(
        name = "Patient",
        description = "Patient management endpoints."
)
@SecurityRequirement(name = "Bearer Authentication")
public class PatientController {

    private final PatientService patientService;
    private final AppointmentService appointmentService;

    @Operation(
            summary = "Create appointment",
            description = "Creates a new appointment for the authenticated patient."
    )
    @ApiResponse(responseCode = "201", description = "Appointment created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @PostMapping("/appointments")
    public ResponseEntity<AppointmentResponseDto> createNewAppointment(
            @RequestBody CreateAppointmentRequestDto createAppointmentRequestDto
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(appointmentService.createNewAppointment(createAppointmentRequestDto));
    }

    @Operation(
            summary = "Get patient profile",
            description = "Returns the authenticated patient's profile."
    )
    @ApiResponse(responseCode = "200", description = "Profile retrieved successfully")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @GetMapping("/profile")
    public ResponseEntity<PatientResponseDto> getPatientProfile() {
        Long patientId = 4L;
        return ResponseEntity.ok(patientService.getPatientById(patientId));
    }

    @Operation(
            summary = "Get appointments by doctor",
            description = "Returns appointments for the specified doctor."
    )
    @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully")
    @GetMapping("/appointments/doctor/{doctorId}")
    public ResponseEntity<List<Appointment>> getAppointmentsByDoctor(
            @Parameter(description = "Doctor ID", example = "1")
            @PathVariable Long doctorId
    ) {
        return ResponseEntity.ok(
                appointmentService.getAppointmentsByDoctorId(doctorId)
        );
    }

    @Operation(
            summary = "Get appointments by date range",
            description = "Returns appointments within the specified date range."
    )
    @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully")
    @GetMapping("/appointments/date-range")
    public ResponseEntity<List<Appointment>> getAppointmentsByDateRange(
            @Parameter(
                    description = "Start date and time",
                    example = "2026-08-01T09:00:00"
            )
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @Parameter(
                    description = "End date and time",
                    example = "2026-08-31T18:00:00"
            )
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end
    ) {
        return ResponseEntity.ok(
                appointmentService.getAppointmentsByDateRange(start, end)
        );
    }
}
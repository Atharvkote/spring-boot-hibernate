package net.spring_boot.hibernate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.spring_boot.hibernate.dto.ops.DoctorResponseDto;
import net.spring_boot.hibernate.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Tag(
        name = "Hospital",
        description = "Public hospital endpoints."
)
public class HospitalController {

    private final DoctorService doctorService;

    @Operation(
            summary = "Get all doctors",
            description = "Returns all registered doctors."
    )
    @ApiResponse(responseCode = "200", description = "Doctors retrieved successfully")
    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorResponseDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @Operation(
            summary = "Get doctors by specialization",
            description = "Returns doctors matching the specified specialization."
    )
    @ApiResponse(responseCode = "200", description = "Doctors retrieved successfully")
    @GetMapping("/doctors/specialization/{specialization}")
    public ResponseEntity<List<?>> getDoctorsBySpecialization(
            @Parameter(
                    description = "Doctor specialization",
                    example = "Cardiology"
            )
            @PathVariable String specialization
    ) {
        return ResponseEntity.ok(
                doctorService.getDoctorsBySpecialization(specialization)
        );
    }
}
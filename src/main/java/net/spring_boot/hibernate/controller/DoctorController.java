package net.spring_boot.hibernate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.spring_boot.hibernate.dto.ops.AppointmentResponseDto;
import net.spring_boot.hibernate.entity.User;
import net.spring_boot.hibernate.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
@Tag(
        name = "Doctor",
        description = "Endpoints for doctors to manage their appointments."
)
@SecurityRequirement(name = "Bearer Authentication")
public class DoctorController {

    private final AppointmentService appointmentService;

    @Operation(
            summary = "Get doctor's appointments",
            description = "Returns all appointments assigned to the currently authenticated doctor."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Appointments retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "User is not authenticated",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Access denied",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content
            )
    })
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointmentsOfDoctor() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(appointmentService.getAllAppointmentsOfDoctor(user.getId()));
    }
}

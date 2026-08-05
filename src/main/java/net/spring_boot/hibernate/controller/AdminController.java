package net.spring_boot.hibernate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.spring_boot.hibernate.dto.ops.DoctorResponseDto;
import net.spring_boot.hibernate.dto.ops.OnboardDoctorRequestDto;
import net.spring_boot.hibernate.dto.ops.PatientResponseDto;
import net.spring_boot.hibernate.entity.Department;
import net.spring_boot.hibernate.entity.Insurance;
import net.spring_boot.hibernate.entity.User;
import net.spring_boot.hibernate.enums.AuthProviderType;
import net.spring_boot.hibernate.enums.RoleType;
import net.spring_boot.hibernate.service.DepartmentService;
import net.spring_boot.hibernate.service.DoctorService;
import net.spring_boot.hibernate.service.InsuranceService;
import net.spring_boot.hibernate.service.PatientService;
import net.spring_boot.hibernate.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(
        name = "Admin",
        description = "Administrative endpoints."
)
@SecurityRequirement(name = "Bearer Authentication")
public class AdminController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final DepartmentService departmentService;
    private final InsuranceService insuranceService;
    private final UserService userService;

    @Operation(summary = "Get all patients")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "500", description = "Server error", content = @Content)
    })
    @GetMapping("/patients")
    public ResponseEntity<List<PatientResponseDto>> getAllPatients(
            @Parameter(description = "Page number", example = "0")
            @RequestParam(defaultValue = "0") Integer page,

            @Parameter(description = "Page size", example = "10")
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ResponseEntity.ok(patientService.getAllPatients(page, size));
    }

    @Operation(summary = "Create a doctor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Doctor created"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @Content),
            @ApiResponse(responseCode = "409", description = "Doctor already exists", content = @Content)
    })
    @PostMapping("/onBoardNewDoctor")
    public ResponseEntity<DoctorResponseDto> onBoardNewDoctor(
            @RequestBody OnboardDoctorRequestDto request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(doctorService.onBoardNewDoctor(request));
    }

    @Operation(summary = "Get all departments")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/departments")
    public ResponseEntity<List<Department>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @Operation(summary = "Get departments with doctors")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/departments/with-doctors")
    public ResponseEntity<List<Department>> getDepartmentsWithDoctors() {
        return ResponseEntity.ok(departmentService.getDepartmentsWithDoctors());
    }

    @Operation(summary = "Get valid insurance policies")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/insurances/valid")
    public ResponseEntity<List<Insurance>> getValidInsurances() {
        return ResponseEntity.ok(
                insuranceService.getValidInsurances(LocalDate.now())
        );
    }

    @Operation(summary = "Get expired insurance policies")
    @ApiResponse(responseCode = "200", description = "Success")
    @GetMapping("/insurances/expired")
    public ResponseEntity<List<Insurance>> getExpiredInsurances() {
        return ResponseEntity.ok(
                insuranceService.getExpiredInsurances(LocalDate.now())
        );
    }

    @Operation(summary = "Get users by role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid role", content = @Content)
    })
    @GetMapping("/users/role/{role}")
    public ResponseEntity<List<User>> getUsersByRole(
            @Parameter(description = "User role", example = "ADMIN")
            @PathVariable RoleType role
    ) {
        return ResponseEntity.ok(userService.getUsersByRole(role));
    }

    @Operation(summary = "Get users by authentication provider")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Invalid provider", content = @Content)
    })
    @GetMapping("/users/provider/{providerType}")
    public ResponseEntity<List<User>> getUsersByProviderType(
            @Parameter(description = "Authentication provider", example = "GOOGLE")
            @PathVariable AuthProviderType providerType
    ) {
        return ResponseEntity.ok(userService.getUsersByProviderType(providerType));
    }
}
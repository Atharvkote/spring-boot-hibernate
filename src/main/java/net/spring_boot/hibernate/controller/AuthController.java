package net.spring_boot.hibernate.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import net.spring_boot.hibernate.dto.auth.LoginRequestDto;
import net.spring_boot.hibernate.dto.auth.LoginResponseDto;
import net.spring_boot.hibernate.dto.auth.SignUpRequestDto;
import net.spring_boot.hibernate.dto.auth.SignupResponseDto;
import net.spring_boot.hibernate.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentication",
        description = "Endpoints for user authentication and registration."
)
public class AuthController {

    private final AuthService authService;

    @Operation(
            summary = "User login",
            description = "Authenticates a user and returns a JWT access token."
    )
    @ApiResponse(responseCode = "200", description = "Login successful")
    @ApiResponse(responseCode = "401", description = "Invalid username or password")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto
    ) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user account."
    )
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "409", description = "User already exists")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(
            @RequestBody SignUpRequestDto signupRequestDto
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.signup(signupRequestDto));
    }
}
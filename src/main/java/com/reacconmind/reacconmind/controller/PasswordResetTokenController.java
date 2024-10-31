package com.reacconmind.reacconmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.reacconmind.reacconmind.service.PasswordResetTokenService;
import com.reacconmind.reacconmind.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Email;

@RestController
@RequestMapping("/passwordResets")
@Tag(name = "Password Reset", description = "Endpoints for requesting and validating password reset tokens.")

public class PasswordResetTokenController {

    @Autowired
    private PasswordResetTokenService passwordResetService;

    @Autowired
    UserService userService;

    @Operation(
        summary = "Request password reset token",
        description = "Generates a password reset token for the specified email address. This allows the user to reset their password."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "Password reset token created successfully."
            ),
            @ApiResponse(
                responseCode = "400",
                description = "Invalid email or user not found. Ensure the email is valid and registered."
            ),
        }
    )
    @PostMapping("/reset-request")
    public ResponseEntity<String> resetPassword(
        @RequestParam @Email String email
    ) {
        String responseMessage = passwordResetService.createPasswordResetToken(
            email
        );

        if ("Request processed".equals(responseMessage)) {
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                responseMessage
            );
        }
    }

    @Operation(
        summary = "Validate password reset token",
        description = "Validates the provided password reset token and allows the user to set a new password."
    )
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200",
                description = "The token is valid and the password has been successfully reset."
            ),
            @ApiResponse(
                responseCode = "400",
                description = "The token is invalid, has expired, or has already been used."
            ),
            @ApiResponse(
                responseCode = "500",
                description = "Internal server error while attempting to reset the password."
            ),
        }
    )
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(
        @RequestParam String token,
        @RequestParam String password
    ) {
        boolean result = passwordResetService.validatePasswordResetToken(
            token,
            password
        );
        return result
            ? ResponseEntity.ok("The password has been successfully reset")
            : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                "Unable to reset the password"
            );
    }
}

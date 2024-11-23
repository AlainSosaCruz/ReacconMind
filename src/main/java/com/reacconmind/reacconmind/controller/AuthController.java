package com.reacconmind.reacconmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reacconmind.reacconmind.dto.AuthResponse;
import com.reacconmind.reacconmind.dto.Login;
import com.reacconmind.reacconmind.service.AuthService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth") // Ruta base para el controlador

public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Login loginRequest) {
        // Llamar al servicio de autenticación
        String response = authService.authenticateUser(loginRequest);

        if (response.startsWith("Login exitoso")) {
            // Devolver respuesta exitosa con mensaje y estado
            AuthResponse authResponse = new AuthResponse(response, true);
            return ResponseEntity.ok(authResponse); // Login exitoso
        } else {
            // Devolver error con mensaje y estado
            AuthResponse authResponse = new AuthResponse(response, false);
            return ResponseEntity.status(401).body(authResponse); // Login fallido
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        // Invalidar la sesión
        session.invalidate();
        return ResponseEntity.ok("Sesión cerrada exitosamente");
    }
}

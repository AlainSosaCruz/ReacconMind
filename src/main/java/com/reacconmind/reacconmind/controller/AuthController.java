package com.reacconmind.reacconmind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reacconmind.reacconmind.dto.Login;
import com.reacconmind.reacconmind.dto.LogoutResponse;
import com.reacconmind.reacconmind.dto.UserAddDTO;
import com.reacconmind.reacconmind.model.AccountUserEmail;
import com.reacconmind.reacconmind.model.User;
import com.reacconmind.reacconmind.response.LoginResponse;
import com.reacconmind.reacconmind.service.AuthService;
import com.reacconmind.reacconmind.util.JwtService;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserAddDTO userDto) {
        User registeredUser = authenticationService.signup(userDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody Login userLoginDto) {
        AccountUserEmail authenticatedUser = authenticationService.authenticate(userLoginDto);
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        // loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7); // Elimina el prefijo "Bearer "

        // Invalidar el token
        jwtService.invalidateToken(jwtToken);

        // Responder con un objeto JSON
        return ResponseEntity.status(HttpStatus.OK).body(new LogoutResponse("Sesión cerrada exitosamente"));
    }
}

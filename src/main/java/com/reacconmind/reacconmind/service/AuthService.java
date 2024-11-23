package com.reacconmind.reacconmind.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.reacconmind.reacconmind.dto.Login;
import com.reacconmind.reacconmind.model.AccountUserEmail;
import com.reacconmind.reacconmind.repository.AccountUserEmailRepository;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AccountUserEmailRepository userEmailRepository;

    @Autowired
    public AuthService(AuthenticationManager authenticationManager,
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            AccountUserEmailRepository userEmailRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.userEmailRepository = userEmailRepository;
    }

    public String authenticateUser(Login loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        try {
            // Buscar usuario por email
            AccountUserEmail user = userEmailRepository
                    .findUserByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

            // Verifica si la contraseña coincide
            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new BadCredentialsException("Password incorrect");
            }

            // Realiza la autenticación
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));

            // Obtén los detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            return "Login exitoso para el usuario: " + userDetails.getUsername();

        } catch (UsernameNotFoundException e) {
            return "Autenticación fallida: El usuario no fue encontrado."; // Mensaje general de error
        } catch (BadCredentialsException e) {
            return "Autenticación fallida: Credenciales incorrectas."; // Mensaje de contraseña incorrecta
        } catch (Exception e) {
            return "Autenticación fallida: " + e.getMessage(); // Cualquier otro error
        }
    }
}

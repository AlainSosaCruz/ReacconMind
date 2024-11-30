package com.reacconmind.reacconmind.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AccountUserEmailAddDTO {

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 50, message = "Email cannot exceed 50 characters")  // Añadido para limitar la longitud del email
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    @Size(max = 255, message = "Password cannot exceed 255 characters")  // Añadido para limitar la longitud de la contraseña
    private String password;

    @NotBlank(message = "User ID cannot be blank") // Añadido para asegurarse de que idUser no sea nulo o vacío
    private int idUser;

    // Constructor vacío
    public AccountUserEmailAddDTO() {
    }

    // Constructor con parámetros
    public AccountUserEmailAddDTO(String email, String password, int idUser) {
        this.email = email;
        this.password = password;
        this.idUser = idUser;
    }

    // Getters y setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}

package com.reacconmind.reacconmind.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AccountUserEmailDTO {
    
    private Integer idUser;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Size(max = 50, message = "Email cannot exceed 50 characters")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password should be at least 6 characters long")
    @Size(max = 255, message = "Password cannot exceed 255 characters")
    private String password;

    // Constructor vacío
    public AccountUserEmailDTO() {
    }

    // Constructor con parámetros
    public AccountUserEmailDTO(Integer idUser, String email, String password) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
    }

    // Getters y setters
    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

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
}

package com.reacconmind.reacconmind.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class GoogleAuth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idGoogleAuth;

    @OneToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser")
    @JsonBackReference
    private User user;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Google ID cannot be blank")
    private String googleId;

    public int getIdGoogleAuth() {
        return idGoogleAuth;
    }

    public void setIdGoogleAuth(int idGoogleAuth) {
        this.idGoogleAuth = idGoogleAuth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

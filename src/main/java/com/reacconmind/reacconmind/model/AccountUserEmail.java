package com.reacconmind.reacconmind.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "AccountUserEmail")
public class AccountUserEmail implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAccountUserEmail;

    private String email;
    private String password;

    @OneToOne
    @JoinColumn(name = "idUser", unique = true, referencedColumnName = "idUser")
    @JsonBackReference
    private User idUser;

    public AccountUserEmail(String email, String password, User idUser) {
        this.email = email;
        this.password = password;
        this.idUser = idUser;
    }

    public AccountUserEmail() {
    }

    public int getIdAccountUserEmail() {
        return idAccountUserEmail;
    }

    public void setIdAccountUserEmail(int idAccountUserEmail) {
        this.idAccountUserEmail = idAccountUserEmail;
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

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Aquí podrías obtener roles desde una base de datos o un sistema externo.
        return List.of(new SimpleGrantedAuthority("ROLE_USER")); // Ejemplo de rol de usuario
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

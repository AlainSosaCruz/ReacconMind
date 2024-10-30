package com.reacconmind.reacconmind.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.sql.Date;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idResetToken;

    @ManyToOne
    @JoinColumn(name = "idAccountUserEmail", nullable = true)
    private AccountUserEmail accountUserEmail;

    @ManyToOne
    @JoinColumn(name = "idGoogleAuth", nullable = true)
    private GoogleAuth googleAuth;
    private String token;
    private Date expirationDate;
    private boolean used;

    public AccountUserEmail getAccountUserEmail() {
        return accountUserEmail;
    }

    public void setAccountUserEmail(AccountUserEmail accountUserEmail) {
        this.accountUserEmail = accountUserEmail;
    }

    public GoogleAuth getGoogleAuth() {
        return googleAuth;
    }

    public void setGoogleAuth(GoogleAuth googleAuth) {
        this.googleAuth = googleAuth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public int getIdResetToken() {
        return idResetToken;
    }

    public void setIdResetToken(int idResetToken) {
        this.idResetToken = idResetToken;
    }
}

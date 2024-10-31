package com.reacconmind.reacconmind.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ProfileColor")
public class ProfileColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idProfileColor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ThemeType theme = ThemeType.Light;

    @OneToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    public ProfileColor() {
    }

    public ProfileColor(ThemeType theme, User user) {
        this.theme = theme;
        this.user = user;
    }

    public int getIdProfileColor() {
        return idProfileColor;
    }

    public void setIdProfileColor(int idProfileColor) {
        this.idProfileColor = idProfileColor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ThemeType getTheme() {
        return theme;
    }

    public void setTheme(ThemeType theme) {
        this.theme = theme;
    }

}

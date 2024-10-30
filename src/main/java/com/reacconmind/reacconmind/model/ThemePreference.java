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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ThemePreference")
public class ThemePreference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idThemePreference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ThemeBotType themeBot = ThemeBotType.CombinatedMedia;

    @ManyToOne
    @JoinColumn(name = "idUser")
    @JsonBackReference
    private User user;

    public ThemePreference(ThemeBotType themeBot, User user) {
        this.themeBot = themeBot;
        this.user = user;
    }

    public ThemePreference() {
    }

    public int getIdThemePreference() {
        return idThemePreference;
    }

    public void setIdThemePreference(int idThemePreference) {
        this.idThemePreference = idThemePreference;
    }

    public ThemeBotType getThemeBot() {
        return themeBot;
    }

    public void setThemeBot(ThemeBotType themeBot) {
        this.themeBot = themeBot;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

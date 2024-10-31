package com.reacconmind.reacconmind.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Bot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idBot;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ThemeBotType theme = ThemeBotType.CombinatedMedia;

    @ManyToOne
    @JoinColumn(name = "idMultimedia", nullable = true)
    private Multimedia multimedia;
    
    public Bot() {}

    public Bot(int idBot, String name, ThemeBotType theme, Multimedia multimedia) {
        this.idBot = idBot;
        this.name = name;
        this.theme = theme;
        this.multimedia = multimedia;
    }



    public int getIdBot() {
        return idBot;
    }

    public void setIdBot(int idBot) {
        this.idBot = idBot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ThemeBotType getTheme() {
        return theme;
    }

    public void setTheme(ThemeBotType theme) {
        this.theme = theme;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
    }
}


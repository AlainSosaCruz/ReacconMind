package com.reacconmind.reacconmind.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

@Entity
@Table(name = "multimedia")
public class Multimedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMultimedia;

    @Column(length = 2083, nullable = false)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MultimediaType type;

    // Constructor que inicializa los atributos correctamente
    public Multimedia(String url, MultimediaType multimediaType) {
        this.url = url;
        this.type = multimediaType;
    }

    // Constructor vacío para JPA
    public Multimedia() {
    }

    // Getters y Setters

    public int getIdMultimedia() {
        return idMultimedia;
    }

    public void setIdMultimedia(int idMultimedia) {
        this.idMultimedia = idMultimedia;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public MultimediaType getType() {
        return type;
    }

    public void setType(MultimediaType type) {
        this.type = type;
    }
}

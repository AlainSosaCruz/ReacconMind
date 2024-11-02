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
import jakarta.persistence.Table;

@Entity
@Table(name = "moderation")
public class Moderation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_moderation")
    private int idModeration;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private int idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_publication", nullable = false)
    private int idPublication;

    @Enumerated(EnumType.STRING)
    @Column(name = "moderation_type", nullable = false)
    private ModerationTypeEnum moderationType;

    // Getters y setters
    public int getIdModeration() {
        return idModeration;
    }

    public void setIdModeration(int idModeration) {
        this.idModeration = idModeration;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUser(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(int idPublication) {
        this.idPublication = idPublication;
    }

    public ModerationTypeEnum getModerationType() {
        return moderationType;
    }

    public void setModerationType(ModerationTypeEnum moderationType) {
        this.moderationType = moderationType;
    }
}

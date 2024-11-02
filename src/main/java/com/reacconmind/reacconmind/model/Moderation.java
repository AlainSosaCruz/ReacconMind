package com.reacconmind.reacconmind.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Moderation")
public class Moderation {

    @EmbeddedId
    private ModerationPK id;

    @Column(name = "moderationDate", nullable = false)
    private Timestamp moderationDate;

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "idUser", nullable = false)
    private User idUser;

    @ManyToOne
    @MapsId("idPublication")
    @JoinColumn(name = "idPublication", nullable = false)
    private Publication idPublication;

    @Enumerated(EnumType.STRING)
    @MapsId("idModerationType")
    @JoinColumn(name = "idModerationType", nullable = false)
    private ModerationType moderationType;

    public Moderation() {
        this.moderationDate = new Timestamp(System.currentTimeMillis());
    }

    public ModerationPK getId() {
        return id;
    }

    public void setId(ModerationPK id) {
        this.id = id;
    }

    public Timestamp getModerationDate() {
        return moderationDate;
    }

    public void setModerationDate(Timestamp moderationDate) {
        this.moderationDate = moderationDate;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Publication getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(Publication idPublication) {
        this.idPublication = idPublication;
    }

    public ModerationType getModerationType() {
        return moderationType;
    }

    public void setModerationType(ModerationType moderationType) {
        this.moderationType = moderationType;
    }
}
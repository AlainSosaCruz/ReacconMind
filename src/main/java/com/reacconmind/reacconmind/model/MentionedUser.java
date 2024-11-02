package com.reacconmind.reacconmind.model;

import jakarta.persistence.Entity;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.IdClass;


@Entity
@Table(
        name = "MentionedUser",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idPublication", "idMentionedUser"}) // Asegura unicidad
)
@IdClass(MentionedUserId.class) // Usar la clase de ID compuesta
public class MentionedUser {

    @Id
    @Column(name = "idPublication", nullable = false)
    private int idPublication;

    @Id
    @Column(name = "idMentionedUser", nullable = false)
    private int idMentionedUser;

    @ManyToOne
    @JoinColumn(name = "idPublication", insertable = false, updatable = false)
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "idMentionedUser", insertable = false, updatable = false)
    private User mentionedUser;

    // Getters y Setters
    public int getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(int idPublication) {
        this.idPublication = idPublication;
    }

    public int getIdMentionedUser() {
        return idMentionedUser;
    }

    public void setIdMentionedUser(int idMentionedUser) {
        this.idMentionedUser = idMentionedUser;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public User getMentionedUser() {
        return mentionedUser;
    }

    public void setMentionedUser(User mentionedUser) {
        this.mentionedUser = mentionedUser;
    }
}

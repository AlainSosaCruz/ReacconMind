package com.reacconmind.reacconmind.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reaction")
public class Reaction {
    @EmbeddedId
    private ReactionPK id;

    @Column(name = "liked", nullable = false)
    private Boolean liked;

    @ManyToOne
    @MapsId("idUser")
    @JoinColumn(name = "idUser", foreignKey = @ForeignKey(name = "FK_User"))
    private User user;

    @ManyToOne
    @MapsId("idPublication")
    @JoinColumn(name = "idPublication", foreignKey = @ForeignKey(name = "FK_Publication"))
    private Publication publication;

    public Reaction(User user, Publication publication, Boolean liked) {
        this.id = new ReactionPK(user.getIdUser(), publication.getIdPublication());
        this.user = user;
        this.publication = publication;
        this.liked = liked;
    }

    public Reaction() {}

    // Getters y Setters
    public ReactionPK getId() {
        return id;
    }

    public void setId(ReactionPK id) {
        this.id = id;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }
}

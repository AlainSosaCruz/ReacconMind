package com.reacconmind.reacconmind.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ForeignKey;

@Entity
@Table(name = "Publication")
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPublication;

    @ManyToOne
    @JoinColumn(name = "idUser", foreignKey = @ForeignKey(name = "fk_user_publication"))
    private User user; // Relación con la entidad User

    @ManyToOne
    @JoinColumn(name = "idBot", foreignKey = @ForeignKey(name = "fk_bot_publication"))
    private Bot bot; // Nueva relación con la entidad Bot

    @OneToMany(mappedBy = "publication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PublicationHashtag> publicationHashtags;

    @Column(length = 250)
    private String content;

    // Getters y Setters
    public Integer getIdPublication() { // Cambiado a Integer
        return idPublication;
    }

    public void setIdPublication(Integer idPublication) { // Cambiado a Integer
        this.idPublication = idPublication;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Bot getBot() {
        return bot;
    }

    public void setBot(Bot bot) {
        this.bot = bot;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

package com.reacconmind.reacconmind.model;


import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idComment;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private     User user; // Relación con la entidad User

    @ManyToOne
    @JoinColumn(name = "idPublication", nullable = false)
    private Publication publication; // Relación con la entidad Publication

    @Column(name = "contentComment", nullable = false, length = 200)
    private String contentComment;



    public Comment(User user, Publication publication, String contentComment) {
        this.user = user;
        this.publication = publication;
        this.contentComment = contentComment;

    }

    public Comment() {
    }

    // Getters y Setters
    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
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

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }


}

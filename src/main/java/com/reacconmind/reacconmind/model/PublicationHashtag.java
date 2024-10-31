package com.reacconmind.reacconmind.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@IdClass(PublicationHashtagPK.class)
@Table(name = "PublicationHashtag")
public class PublicationHashtag {
    @Id
    @Column(name = "idPublication", insertable = false, updatable = false)
    private Integer idPublication;  // Agregamos este campo

    @Id
    @Column(name = "idHashtag", insertable = false, updatable = false)
    private Integer idHashtag;  // Agregamos este campo

    @ManyToOne
    @JoinColumn(name = "idPublication", referencedColumnName = "idPublication", insertable = false, updatable = false)
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "idHashtag", referencedColumnName = "idHashtag", insertable = false, updatable = false)
    private Hashtag hashtag;

    // Constructor vacío
    public PublicationHashtag() {}

    // Constructor con parámetros
    public PublicationHashtag(Publication publication, Hashtag hashtag) {
        this.publication = publication;
        this.hashtag = hashtag;
        this.idPublication = publication.getIdPublication();
        this.idHashtag = hashtag.getIdHashtag();
    }

    // Getters y setters
    public Integer getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(Integer idPublication) {
        this.idPublication = idPublication;
    }

    public Integer getIdHashtag() {
        return idHashtag;
    }

    public void setIdHashtag(Integer idHashtag) {
        this.idHashtag = idHashtag;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

    public Hashtag getHashtag() {
        return hashtag;
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }
}

    

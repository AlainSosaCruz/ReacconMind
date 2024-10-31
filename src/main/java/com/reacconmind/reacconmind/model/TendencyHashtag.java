package com.reacconmind.reacconmind.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@IdClass(TendencyHashtagPK.class)
@Table(name = "TendencyHashtag")
public class TendencyHashtag {
    @Id
    @Column(name = "idTendency", insertable = false, updatable = false)
    private int idTendency;

    @Id
    @Column(name = "idHashtag", insertable = false, updatable = false)
    private int idHashtag;

    @ManyToOne
    @JoinColumn(name = "idTendency", referencedColumnName = "idTendency", insertable = false, updatable = false)
    private Tendency tendency;

    @ManyToOne
    @JoinColumn(name = "idHashtag", referencedColumnName = "idHashtag", insertable = false, updatable = false)
    private Hashtag hashtag;

    // Constructor vacío
    public TendencyHashtag() {}

    // Constructor con parámetros
    public TendencyHashtag(Tendency tendency, Hashtag hashtag) {
        this.tendency = tendency;
        this.hashtag = hashtag;
        this.idTendency = tendency.getIdTendency();
        this.idHashtag = hashtag.getIdHashtag();
    }

    // Getters y setters
    public int getIdTendency() {
        return idTendency;
    }

    public void setIdTendency(int idTendency) {
        this.idTendency = idTendency;
    }

    public int getIdHashtag() {
        return idHashtag;
    }

    public void setIdHashtag(int idHashtag) {
        this.idHashtag = idHashtag;
    }

    public Tendency getTendency() {
        return tendency;
    }

    public void setTendency(Tendency tendency) {
        this.tendency = tendency;
    }

    public Hashtag getHashtag() {
        return hashtag;
    }

    public void setHashtag(Hashtag hashtag) {
        this.hashtag = hashtag;
    }
}

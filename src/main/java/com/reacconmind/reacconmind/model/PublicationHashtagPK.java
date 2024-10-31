package com.reacconmind.reacconmind.model;

import java.io.Serializable;
import java.util.Objects;

public class PublicationHashtagPK implements Serializable {
    private Integer idPublication;  // Cambiado a Integer
    private Integer idHashtag;      // Cambiado a Integer

    public PublicationHashtagPK() {}

    public PublicationHashtagPK(Integer idPublication, Integer idHashtag) {
        this.idPublication = idPublication;
        this.idHashtag = idHashtag;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PublicationHashtagPK)) return false;
        PublicationHashtagPK that = (PublicationHashtagPK) o;
        return Objects.equals(idPublication, that.idPublication) && Objects.equals(idHashtag, that.idHashtag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPublication, idHashtag);
    }
}

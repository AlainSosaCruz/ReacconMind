package com.reacconmind.reacconmind.model;

import java.io.Serializable;
import java.util.Objects;

public class TendencyHashtagPK implements Serializable {
    private int idTendency;
    private int idHashtag;

    public TendencyHashtagPK() {}

    public TendencyHashtagPK(int idTendency, int idHashtag) {
        this.idTendency = idTendency;
        this.idHashtag = idHashtag;
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

    // Equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TendencyHashtagPK that = (TendencyHashtagPK) o;
        return idTendency == that.idTendency && idHashtag == that.idHashtag;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTendency, idHashtag);
    }

    
}

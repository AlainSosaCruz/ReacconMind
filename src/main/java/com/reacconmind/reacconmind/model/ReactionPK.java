package com.reacconmind.reacconmind.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class ReactionPK implements Serializable {
    private int idUser;
    private int idPublication;

    public ReactionPK() {}

    public ReactionPK(int idUser, int idPublication) {
        this.idUser = idUser;
        this.idPublication = idPublication;
    }

    // Getters y Setters
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(int idPublication) {
        this.idPublication = idPublication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReactionPK that = (ReactionPK) o;
        return idUser == that.idUser && idPublication == that.idPublication;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idPublication);
    }
}

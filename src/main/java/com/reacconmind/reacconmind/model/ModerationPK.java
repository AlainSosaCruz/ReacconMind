package com.reacconmind.reacconmind.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class ModerationPK implements Serializable {
    private int idUser;
    private int idPublication;
    private String idModerationType;

    public ModerationPK() {
    }

    public ModerationPK(int idUser, int idPublication, String idModerationType) {
        this.idUser = idUser;
        this.idPublication = idPublication;
        this.idModerationType = idModerationType;
    }

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

    public String getIdModerationType() {
        return idModerationType;
    }

    public void setIdModerationType(String idModerationType) {
        this.idModerationType = idModerationType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModerationPK)) return false;
        ModerationPK that = (ModerationPK) o;
        return idUser == that.idUser && idPublication == that.idPublication && Objects.equals(idModerationType, that.idModerationType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUser, idPublication, idModerationType);
    }
}
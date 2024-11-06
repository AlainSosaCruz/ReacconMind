package com.reacconmind.reacconmind.dto;

public class ReactionDTO {

    private int idUser;
    private int idPublication;
    private boolean liked;


    // Constructor
    public ReactionDTO(int idUser, int idPublication, boolean liked) {
        this.idUser = idUser;
        this.idPublication = idPublication;
        this.liked = liked;

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

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }


}

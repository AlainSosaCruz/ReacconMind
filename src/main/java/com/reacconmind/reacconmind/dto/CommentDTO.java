package com.reacconmind.reacconmind.dto;

public class CommentDTO {
    private int idComment;
    private int idUser;
    private int idPublication;
    private String contentComment;

    // Constructor

    public CommentDTO(int idComment, int idUser, int idPublication, String contentComment) {
        this.idComment = idComment;
        this.idUser = idUser;
        this.idPublication = idPublication;
        this.contentComment = contentComment;
    }

    public CommentDTO() {
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
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

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }
}

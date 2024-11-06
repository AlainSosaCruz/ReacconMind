package com.reacconmind.reacconmind.dto;

public class ReplyDTO {
    private int idReply;
    private int idUser;
    private int idComment;
    private String contentReply;

    // Constructor
    public ReplyDTO(int idReply, int idUser, int idComment, String contentReply) {
        this.idReply = idReply;
        this.idUser = idUser;
        this.idComment = idComment;
        this.contentReply = contentReply;
    }

    // Getters y Setters
    public int getIdReply() {
        return idReply;
    }

    public void setIdReply(int idReply) {
        this.idReply = idReply;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public String getContentReply() {
        return contentReply;
    }

    public void setContentReply(String contentReply) {
        this.contentReply = contentReply;
    }
}

package com.reacconmind.reacconmind.dto;

public class CommentDTO {
    private int idComment;
    private String userName;
    private int idPublication;
    private String content;
    private String contentComment;

    // Constructor


    public CommentDTO(int idComment, String userName, int idPublication, String content, String contentComment) {
        this.idComment = idComment;
        this.userName = userName;
        this.idPublication = idPublication;
        this.content = content;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(int idPublication) {
        this.idPublication = idPublication;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }
}

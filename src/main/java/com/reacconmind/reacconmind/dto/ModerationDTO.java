package com.reacconmind.reacconmind.dto;

public class ModerationDTO {
    private int publicationId;
    private int userId;
    private String content;

    // Constructor
    public ModerationDTO(int publicationId, int userId, String content) {
        this.publicationId = publicationId;
        this.userId = userId;
        this.content = content;
    }

    // Getters
    public int getPublicationId() {
        return publicationId;
    }

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    // Setters
    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

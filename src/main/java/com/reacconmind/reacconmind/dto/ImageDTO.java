package com.reacconmind.reacconmind.dto;

public class ImageDTO {
    private int idImage;
    private String url;
    private String thumbnail;
    private int idPublication;
    private String userName;

    public ImageDTO(int idImage, String url, String thumbnail, int idPublication, String userName) {
        this.idImage = idImage;
        this.url = url;
        this.thumbnail = thumbnail;
        this.idPublication = idPublication;
        this.userName = userName;
    }

    public int getIdImage() {
        return idImage;
    }

    public void setIdImage(int idImage) {
        this.idImage = idImage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(int idPublication) {
        this.idPublication = idPublication;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

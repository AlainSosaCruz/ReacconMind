package com.reacconmind.reacconmind.dto;

public class UserAddDTO {
    private String name;
    private String userName;
    private String imageProfile;
    private String imageFacade;
    private String thumbnail;
    private String biography;

    public UserAddDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getImageFacade() {
        return imageFacade;
    }

    public void setImageFacade(String imageFacade) {
        this.imageFacade = imageFacade;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

}

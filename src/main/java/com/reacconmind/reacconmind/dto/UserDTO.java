package com.reacconmind.reacconmind.dto;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.reacconmind.reacconmind.model.AccountUserEmail;
import com.reacconmind.reacconmind.model.GoogleAuth;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private int idUser;
    private String name;
    private String email;
    private String userName;
    private String biography;
    private String imageProfile;
    private String imageFacade;
    private String thumbnail;
    private ProfileColorDTO profileColor;
    private List<ThemePreferenceDTO> themePreferences = new ArrayList<>();
    private Date dateCreationProfile;
   // private AccountUserEmail accountUserEmail;
    private GoogleAuth accountUserGoogle;

  /*   public AccountUserEmail getAccountUserEmail() {
        return accountUserEmail;
    }

    public void setAccountUserEmail(AccountUserEmail accountUserEmail) {
        this.accountUserEmail = accountUserEmail;
    } */

    public GoogleAuth getAccountUserGoogle() {
        return accountUserGoogle;
    }

    public void setAccountUserGoogle(GoogleAuth accountUserGoogle) {
        this.accountUserGoogle = accountUserGoogle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
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

    public List<ThemePreferenceDTO> getThemePreferences() {
        return themePreferences;
    }

    public void setThemePreferences(List<ThemePreferenceDTO> themePreferences) {
        this.themePreferences = themePreferences;
    }

    public Date getDateCreationProfile() {
        return dateCreationProfile;
    }

    public void setDateCreationProfile(Date dateCreationProfile) {
        this.dateCreationProfile = dateCreationProfile;
    }

    public void setProfileColor(ProfileColorDTO profileColor) {
        this.profileColor = profileColor;
    }

    public ProfileColorDTO getProfileColor() {
        return profileColor;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

}

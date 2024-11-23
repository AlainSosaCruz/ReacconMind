package com.reacconmind.reacconmind.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idUser;

    private String name;

    private String imageProfile = "https://firebasestorage.googleapis.com/v0/b/reacconmind-e99ee.appspot.com/o/8936051b-d9c1-42d4-a9f0-f522116700ee.png?alt=media&token=31a4ea56-5d4c-411f-b253-c191464b7a9e";
    private String imageFacade = "https://firebasestorage.googleapis.com/v0/b/reacconmind-e99ee.appspot.com/o/cd16b824-7188-473b-b991-22d09edb98e0.jpg?alt=media&token=92b3ddb6-a9e9-4f17-b10f-9675c0f72d58";

    private String thumbnail = "https://firebasestorage.googleapis.com/v0/b/reacconmind-e99ee.appspot.com/o/thumb_8936051b-d9c1-42d4-a9f0-f522116700ee.png?alt=media&token=f19a1d1d-2769-46c8-acd4-66582ddab82e";
    private String biography;

    private String userName;

    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.Active;

    private Date dateCreationProfile;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ProfileColor profileColor;

    @JsonManagedReference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ThemePreference> themePreferences = new ArrayList<>();

    @JsonManagedReference
    @OneToOne(mappedBy = "idUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private AccountUserEmail accountUserEmail;

    @JsonManagedReference
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private GoogleAuth accountUserGoogle;

    public User() {
    }

    public User(String name, String imageProfile, String imageFacade, String thumbnail, String biography,
            String userName, StatusType status) {
        this.name = name;
        this.imageProfile = imageProfile;
        this.imageFacade = imageFacade;
        this.thumbnail = thumbnail;
        this.biography = biography;
        this.userName = userName;
        this.status = status;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Date getDateCreationProfile() {
        return dateCreationProfile;
    }

    public void setDateCreationProfile(Date dateCreationProfile) {
        this.dateCreationProfile = dateCreationProfile;
    }

    public ProfileColor getProfileColor() {
        return profileColor;
    }

    public void setProfileColor(ProfileColor profileColor) {
        this.profileColor = profileColor;
    }

    public AccountUserEmail getAccountUserEmail() {
        return accountUserEmail;
    }

    public void setAccountUserEmail(AccountUserEmail accountUserEmail) {
        this.accountUserEmail = accountUserEmail;
    }

    public GoogleAuth getAccountUserGoogle() {
        return accountUserGoogle;
    }

    public void setAccountUserGoogle(GoogleAuth accountUserGoogle) {
        this.accountUserGoogle = accountUserGoogle;
    }

    public List<ThemePreference> getThemePreferences() {
        return themePreferences;
    }

    public void setThemePreferences(List<ThemePreference> themePreferences) {
        this.themePreferences = themePreferences;
    }

}

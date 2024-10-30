package com.reacconmind.reacconmind.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonIgnore
    private int idUser;

    private String name;

    private String email;

    private String password;
    @JsonIgnore
    private String imageProfile = "https://firebasestorage.googleapis.com/v0/b/reacconmind-e99ee.appspot.com/o/8936051b-d9c1-42d4-a9f0-f522116700ee.png?alt=media&token=31a4ea56-5d4c-411f-b253-c191464b7a9e";
    @JsonIgnore
    private String imageFacade = "https://firebasestorage.googleapis.com/v0/b/reacconmind-e99ee.appspot.com/o/cd16b824-7188-473b-b991-22d09edb98e0.jpg?alt=media&token=92b3ddb6-a9e9-4f17-b10f-9675c0f72d58";

    private String thumbnail = "https://firebasestorage.googleapis.com/v0/b/reacconmind-e99ee.appspot.com/o/thumb_8936051b-d9c1-42d4-a9f0-f522116700ee.png?alt=media&token=f19a1d1d-2769-46c8-acd4-66582ddab82e";
    private String biography;

    private String userName;

    //@JsonIgnore
    @Enumerated(EnumType.STRING)
    private AuthType typeAuth = AuthType.Email;
    //@JsonIgnore
    @Enumerated(EnumType.STRING)
    private StatusType status = StatusType.Active;

    @Enumerated(EnumType.STRING)
    private ThemeType theme = ThemeType.Light;

    @Enumerated(EnumType.STRING)
    private ThemeBotType themeBot = ThemeBotType.CombinatedMedia;

    @OneToMany(mappedBy = "sender") 
    private List<Message> sentMessages;

    @OneToMany(mappedBy = "addressee") 
    private List<Message> receivedMessages;

    public User() {
    }

    public User(int idUser, String name, String email, String password, String imageProfile, String imageFacade,
            String thumbnail, String biography, String userName, AuthType typeAuth, StatusType status, ThemeType theme,
            ThemeBotType themeBot) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageProfile = imageProfile;
        this.imageFacade = imageFacade;
        this.thumbnail = thumbnail;
        this.biography = biography;
        this.userName = userName;
        this.typeAuth = typeAuth;
        this.status = status;
        this.theme = theme;
        this.themeBot = themeBot;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public AuthType getTypeAuth() {
        return typeAuth;
    }

    public void setTypeAuth(AuthType typeAuth) {
        this.typeAuth = typeAuth;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public ThemeType getTheme() {
        return theme;
    }

    public void setTheme(ThemeType theme) {
        this.theme = theme;
    }

    public ThemeBotType getThemeBot() {
        return themeBot;
    }

    public void setThemeBot(ThemeBotType themeBot) {
        this.themeBot = themeBot;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}

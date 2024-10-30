package com.reacconmind.reacconmind.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ProfileColorAddDTO {
    private int idUser;
    private String theme;
    @JsonIgnore
    private int idProfileColor;

    public ProfileColorAddDTO() {
    }

    public ProfileColorAddDTO(int idUser, String theme, int idProfileColor) {
        this.idUser = idUser;
        this.theme = theme;
        this.idProfileColor = idProfileColor;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getIdProfileColor() {
        return idProfileColor;
    }

    public void setIdProfileColor(int idProfileColor) {
        this.idProfileColor = idProfileColor;
    }

}

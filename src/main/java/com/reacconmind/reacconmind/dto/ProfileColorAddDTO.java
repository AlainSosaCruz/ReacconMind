package com.reacconmind.reacconmind.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProfileColorAddDTO {
    
    @NotNull(message = "User ID cannot be null")
    private Integer idUser;

    @NotBlank(message = "Theme cannot be blank")
    @Size(max = 10, message = "Theme cannot exceed 10 characters")
    private String theme;

    @JsonIgnore
    private int idProfileColor;

    public ProfileColorAddDTO() {
    }

    public ProfileColorAddDTO(Integer idUser, String theme, int idProfileColor) {
        this.idUser = idUser;
        this.theme = theme;
        this.idProfileColor = idProfileColor;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
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

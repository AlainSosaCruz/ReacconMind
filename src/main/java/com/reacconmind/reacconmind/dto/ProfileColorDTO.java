package com.reacconmind.reacconmind.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ProfileColorDTO {
    
    @NotBlank(message = "Theme cannot be blank")
    @Size(max = 10, message = "Theme cannot exceed 10 characters")
    private String theme;

    public ProfileColorDTO() {
    }

    public ProfileColorDTO(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
}

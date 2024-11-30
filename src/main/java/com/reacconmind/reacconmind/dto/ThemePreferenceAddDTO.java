package com.reacconmind.reacconmind.dto;

import com.reacconmind.reacconmind.model.ThemeBotType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;

public class ThemePreferenceAddDTO {

    private Integer idThemePreference;

    @NotNull(message = "ThemeBot cannot be null")
    private ThemeBotType themeBot;

    @Min(value = 1, message = "User ID must be a positive number")
    private Integer userId;

    public ThemePreferenceAddDTO(Integer idThemePreference, ThemeBotType themeBot, Integer userId) {
        this.idThemePreference = idThemePreference;
        this.themeBot = themeBot;
        this.userId = userId;
    }

    public ThemePreferenceAddDTO() {
    }

    public Integer getIdThemePreference() {
        return idThemePreference;
    }

    public void setIdThemePreference(Integer idThemePreference) {
        this.idThemePreference = idThemePreference;
    }

    public ThemeBotType getThemeBot() {
        return themeBot;
    }

    public void setThemeBot(ThemeBotType themeBot) {
        this.themeBot = themeBot;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

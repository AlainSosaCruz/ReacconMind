package com.reacconmind.reacconmind.dto;

import com.reacconmind.reacconmind.model.ThemeBotType;

public class ThemePreferenceAddDTO {
 private Integer idThemePreference;
    private ThemeBotType themeBot;
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

package com.reacconmind.reacconmind.dto;

import com.reacconmind.reacconmind.model.ThemeBotType;

public class ThemePreferenceDTO {
    private ThemeBotType themeBot;

    public ThemePreferenceDTO() {
    }

    public ThemePreferenceDTO(ThemeBotType themeBot) {
        this.themeBot = themeBot;
    }

    public ThemeBotType getThemeBot() {
        return themeBot;
    }

    public void setThemeBot(ThemeBotType themeBot) {
        this.themeBot = themeBot;
    }
}

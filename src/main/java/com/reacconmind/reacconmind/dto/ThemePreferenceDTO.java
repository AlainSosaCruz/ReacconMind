package com.reacconmind.reacconmind.dto;

import com.reacconmind.reacconmind.model.ThemeBotType;
import jakarta.validation.constraints.NotNull;

public class ThemePreferenceDTO {
    
    @NotNull(message = "ThemeBot cannot be null")
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

package com.reacconmind.reacconmind.dto;

import com.reacconmind.reacconmind.model.ThemeBotType;

public class PublicationDTO {
    private int idPublication;

    private String userName;

    private String botName;
    private ThemeBotType botTheme;

    private String content;


    public PublicationDTO(int idPublication, String userName, String botName, ThemeBotType botTheme, String content) {
        this.idPublication = idPublication;
        this.userName = userName;
        this.botName = botName;
        this.botTheme = botTheme;
        this.content = content;
    }

    public int getIdPublication() {
        return idPublication;
    }

    public void setIdPublication(int idPublication) {
        this.idPublication = idPublication;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public ThemeBotType getBotTheme() {
        return botTheme;
    }

    public void setBotTheme(ThemeBotType botTheme) {
        this.botTheme = botTheme;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

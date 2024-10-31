package com.reacconmind.reacconmind.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reacconmind.reacconmind.model.Multimedia;
import com.reacconmind.reacconmind.model.ThemeBotType;

public class BotDTO {

    @JsonProperty("idBot")
    private int idBot;
    @JsonProperty("name")
    private String name;
    @JsonProperty("theme")
    private ThemeBotType theme;
    @JsonProperty("shippingDate")
    private Timestamp shippingDate;
    private Multimedia multimedia;

    public BotDTO() {
    }

    public BotDTO(String name, ThemeBotType theme, Timestamp shippingDate) {
        this.name = name;
        this.theme = theme;
        this.shippingDate = shippingDate;
    }

    public int getIdBot() {
        return idBot;
    }

    public void setIdBot(int idBot) {
        this.idBot = idBot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ThemeBotType getTheme() {
        return theme;
    }

    public void setTheme(ThemeBotType theme) {
        this.theme = theme;
    }

    public Timestamp getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Timestamp shippingDate) {
        this.shippingDate = shippingDate;
    }

    public Multimedia getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Multimedia multimedia) {
        this.multimedia = multimedia;
    }

    @Override
    public String toString() {
        return "BotDTO{" +
                "idBot=" + idBot +
                ", name='" + name + '\'' +
                ", theme=" + theme +
                ", shippingDate=" + shippingDate +
                '}';
    }
}

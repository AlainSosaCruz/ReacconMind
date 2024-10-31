package com.reacconmind.reacconmind.dto;

import com.reacconmind.reacconmind.model.User;

import java.sql.Date;

import com.reacconmind.reacconmind.model.NotificationStatus;
import com.reacconmind.reacconmind.model.TypeNotification;

public class NotificationDTO {
    private int idNotification;
    private User idUser;
    private TypeNotification typeNotification;
    private String content;
    private NotificationStatus state = NotificationStatus.Unread;
    private Date dateNotification;
    
    public int getIdNotification() {
        return idNotification;
    }
    public void setIdNotification(int idNotification) {
        this.idNotification = idNotification;
    }
    public User getIdUser() {
        return idUser;
    }
    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }
    public TypeNotification getTypeNotification() {
        return typeNotification;
    }
    public void setTypeNotification(TypeNotification typeNotification) {
        this.typeNotification = typeNotification;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public NotificationStatus getState() {
        return state;
    }
    public void setState(NotificationStatus state) {
        this.state = state;
    }
    public Date getDateNotification() {
        return dateNotification;
    }
    public void setDateNotification(Date dateNotification) {
        this.dateNotification = dateNotification;
    }
}

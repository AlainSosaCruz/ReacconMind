package com.reacconmind.reacconmind.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idNotification;

    @ManyToOne
    @JoinColumn(name = "idUser", nullable = false)
    private User idUser;

    @Enumerated(EnumType.STRING)
    private TypeNotification typeNotification;

    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationStatus state = NotificationStatus.Unread;

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

    @Override
    public String toString() {
        return (
            idNotification +
            " :: " +
            typeNotification +
            " :: " +
            content +
            " :: " +
            state +
            " :: " 
        );
    }
}
package com.reacconmind.reacconmind.model;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idMessage; 

    @ManyToOne
    @JoinColumn(name = "idSender", nullable = false) 
    private User sender; 

    @ManyToOne
    @JoinColumn(name = "idAddressee", nullable = false) 
    private User addressee; 


    @Column(nullable = false)
    private String content;     

    private String multimedia;

    public Message() {}
    
    public Message(User sender, User addressee, String content, String multimedia) {
        this.sender = sender;
        this.addressee = addressee;
        this.content = content;
        this.multimedia = multimedia;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getAddressee() {
        return addressee;
    }

    public void setAddressee(User addressee) {
        this.addressee = addressee;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(String multimedia) {
        this.multimedia = multimedia;
    }

}

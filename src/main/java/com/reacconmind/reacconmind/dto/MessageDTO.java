package com.reacconmind.reacconmind.dto;


import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;

public class MessageDTO {
    private int idMessage;
    private int sender;
    private int addressee;
    private String content;
    private String multimedia;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private Timestamp shippingDate;
    

    public MessageDTO() {}

    public MessageDTO(int idMessage, int sender, int addressee, String content, String multimedia, Timestamp shippingDate) {
        this.idMessage = idMessage;
        this.sender = sender;
        this.addressee = addressee;
        this.content = content;
        this.multimedia = multimedia;
        this.shippingDate = shippingDate;
    }

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getAddressee() {
        return addressee;
    }

    public void setAddressee(int addressee) {
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

    public Timestamp getShippingDate() {
        return shippingDate;
    }

    public void setShippingDate(Timestamp shippingDate) {
        this.shippingDate = shippingDate;
    }
}

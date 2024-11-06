package com.reacconmind.reacconmind.dto;

import com.reacconmind.reacconmind.model.Publication;
import java.time.LocalDateTime;

public class PublicationDTO {
    private int idPublication;
    private int idUser;
    private int idBot;
    private String content;


    // Constructor para crear el DTO con todos los campos
    public PublicationDTO(int idPublication, int idUser, int idBot, String content) {
        this.idPublication = idPublication;
        this.idUser = idUser;
        this.idBot = idBot;
        this.content = content;

    }

    // Constructor que toma una entidad Publication
    public PublicationDTO(Publication publication) {
        this.idPublication = publication.getIdPublication();
        this.idUser = publication.getUser().getIdUser();
        this.idBot = publication.getBot().getIdBot();
        this.content = publication.getContent();

    }

    // Getters y Setters
    public int getIdPublication() { return idPublication; }
    public void setIdPublication(int idPublication) { this.idPublication = idPublication; }

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }

    public int getIdBot() { return idBot; }
    public void setIdBot(int idBot) { this.idBot = idBot; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }


}

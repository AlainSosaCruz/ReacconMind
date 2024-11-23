package com.reacconmind.reacconmind.dto;



public class AccountUserEmailAddDTO {

    private String email;

    private String password;

    private int idUser;


    public AccountUserEmailAddDTO() {
    }


    public AccountUserEmailAddDTO(String email, String password, int idUser) {
        this.email = email;
        this.password = password;
        this.idUser = idUser;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public int getIdUser() {
        return idUser;
    }


    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    
    
}

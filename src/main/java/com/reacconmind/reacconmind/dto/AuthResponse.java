package com.reacconmind.reacconmind.dto;

public class AuthResponse {
    private String message;
    private boolean success;

    // Constructor, getters y setters
    public AuthResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}

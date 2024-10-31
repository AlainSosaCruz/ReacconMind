package com.reacconmind.reacconmind.model;

public enum MultimediaType {
    IMAGE,
    VIDEO,
    AUDIO,
    OTHER;

    public static MultimediaType fromFileName(String fileName) {
        String extension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        switch (extension) {
            case ".png":
            case ".jpg":
            case ".jpeg":
            case ".gif":
                return IMAGE;
            case ".mp4":
            case ".avi":
            case ".mov":
            case ".mkv":
                return VIDEO;
            case ".mp3":
            case ".wav":
            case ".aac":
                return AUDIO;
            default:
                return OTHER;
        }
    }
}

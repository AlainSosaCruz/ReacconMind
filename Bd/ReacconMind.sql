CREATE DATABASE reacconMind;
USE reacconMind;
-- USE sys;
-- DROP DATABASE reacconMind;

CREATE TABLE User (
    idUser INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    imageProfile VARCHAR(2083) NOT NULL, -- URL para la imagen de perfil
    imageFacade VARCHAR(2083) NOT NULL, -- URL para la imagen de fachada
    biography VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    thumbnail VARCHAR(2083),
    dateCreationProfile TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status ENUM ('Active','Inactive') DEFAULT 'Active'
);

CREATE TABLE ThemePreference (
    idThemePreference INT PRIMARY KEY AUTO_INCREMENT,
    themeBot ENUM('Sports', 'Technology', 'News', 'Music', 'Movies', 'CombinatedMedia') NOT NULL DEFAULT 'CombinatedMedia',
    idUser INT,
    FOREIGN KEY (idUser) REFERENCES User(idUser)
);	
CREATE TABLE ProfileColor (
    idProfileColor INT PRIMARY KEY AUTO_INCREMENT,
    theme ENUM('Dark', 'Light') NOT NULL DEFAULT 'Light',
    idUser INT UNIQUE,
    FOREIGN KEY (idUser) REFERENCES User(idUser)
);

CREATE TABLE AccountUserEmail (
    idAccountUserEmail INT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    idUser INT UNIQUE,
    FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE
);
CREATE TABLE GoogleAuth (
    idGoogleAuth INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT NOT NULL,
    googleId VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE
);

CREATE TABLE PasswordResetToken (
    idResetToken INT PRIMARY KEY AUTO_INCREMENT,
    idAccountUserEmail INT,
    idGoogleAuth INT,
    token VARCHAR(255) NOT NULL UNIQUE,
    expirationDate TIMESTAMP NOT NULL DEFAULT (DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 20 MINUTE)),
    used BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (idAccountUserEmail) REFERENCES AccountUserEmail(idAccountUserEmail) ON DELETE CASCADE,
    FOREIGN KEY (idGoogleAuth) REFERENCES GoogleAuth(idGoogleAuth) ON DELETE CASCADE,
    CHECK ((idAccountUserEmail IS NOT NULL AND idGoogleAuth IS NULL) OR (idAccountUserEmail IS NULL AND idGoogleAuth IS NOT NULL))
);

INSERT INTO ThemePreference (themeBot, idUser) 
VALUES 
('Sports', 1),
('Technology', 2),
('Music', 4);


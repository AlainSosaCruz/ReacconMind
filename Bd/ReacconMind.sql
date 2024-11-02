
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


CREATE TABLE Multimedia (
    idMultimedia INT PRIMARY KEY AUTO_INCREMENT,
    url VARCHAR(2083) NOT NULL,
    type ENUM('Image', 'Video', 'Audio') NOT NULL, -- Tipo de multimedia
    uploadDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Message (
    idMessage INT PRIMARY KEY AUTO_INCREMENT,
    idSender INT NOT NULL,
    idAddressee INT NOT NULL,
    content TEXT NOT NULL,
    multimedia VARCHAR(255),
    shippingDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idSender) REFERENCES User(idUser) ON DELETE CASCADE,
    FOREIGN KEY (idAddressee) REFERENCES User(idUser) ON DELETE CASCADE,
    CHECK (idSender <> idAddressee) -- Asegura que no se envíen mensajes a sí mismos
);


CREATE TABLE Bot (
    idBot INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    theme ENUM('Sports', 'Technology', 'News', 'Music', 'Movies', 'CombinatedMedia') NOT NULL DEFAULT 'CombinatedMedia',
    idMultimedia INT,
    shippingDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idMultimedia) REFERENCES Multimedia(idMultimedia) ON DELETE SET NULL
);

CREATE TABLE Publication (
    idPublication INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT,
    idBot INT, -- Nuevo campo para relación con bot
    content VARCHAR(250),
    publicationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE,
    FOREIGN KEY (idBot) REFERENCES Bot(idBot) ON DELETE CASCADE, -- Relación entre publicación y bot
    CONSTRAINT checkUserOrBot
    CHECK (idUser IS NOT NULL OR idBot IS NOT NULL)  -- Se asegura que al menos uno esté presente
);

CREATE TABLE Follower (
    idUserFollower INT NOT NULL,         -- El usuario que sigue
    idFollowing INT NOT NULL,            -- El usuario o bot seguido
    followingType ENUM('User', 'Bot') NOT NULL,  -- Indica si el seguido es un usuario o un bot
    dateFollowing TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,  
    PRIMARY KEY (idUserFollower, idFollowing, followingType),  
    FOREIGN KEY (idUserFollower) REFERENCES User(idUser) ON DELETE CASCADE,  -- El seguidor debe ser un usuario
    -- Validación basada en el tipo de seguidor
    FOREIGN KEY (idFollowing) REFERENCES User(idUser) ON DELETE CASCADE,  
    FOREIGN KEY (idFollowing) REFERENCES Bot(idBot) ON DELETE CASCADE     
);

CREATE TABLE Image (
    idImage INT PRIMARY KEY AUTO_INCREMENT,
    url VARCHAR(2083) NOT NULL,
    thumbnail VARCHAR(2083),
    uploadDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    idPublication INT,
    FOREIGN KEY (idPublication) REFERENCES Publication(idPublication) ON DELETE CASCADE
);

CREATE TABLE Hashtag (
    idHashtag INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE PublicationHashtag (
    idPublication INT NOT NULL,
    idHashtag INT NOT NULL,
    FOREIGN KEY (idPublication) REFERENCES Publication(idPublication) ON DELETE CASCADE,
    FOREIGN KEY (idHashtag) REFERENCES Hashtag(idHashtag) ON DELETE CASCADE,
    PRIMARY KEY (idPublication, idHashtag) -- Llave primaria compuesta
);

CREATE TABLE Tendency (
    idTendency INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    dateBegin TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    dateEnd TIMESTAMP,
    CHECK (dateEnd IS NULL OR dateEnd >= dateBegin)
);

CREATE TABLE TendencyHashtag (
    idTendency INT NOT NULL,
    idHashtag INT NOT NULL,
    FOREIGN KEY (idTendency) REFERENCES Tendency(idTendency) ON DELETE CASCADE,
    FOREIGN KEY (idHashtag) REFERENCES Hashtag(idHashtag) ON DELETE CASCADE,
    PRIMARY KEY (idTendency, idHashtag) -- Llave primaria compuesta
);

CREATE TABLE ModerationType (
    idModerationType INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Moderation (
    idUser INT NOT NULL,
    idPublication INT NOT NULL,
    idModerationType INT NOT NULL, -- Referencia a la tabla de tipos de moderación
    moderationDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE,
    FOREIGN KEY (idPublication) REFERENCES Publication(idPublication) ON DELETE CASCADE,
    FOREIGN KEY (idModerationType) REFERENCES ModerationType(idModerationType) ON DELETE CASCADE,
    UNIQUE (idUser, idPublication, idModerationType), -- Llave única para evitar duplicados
    PRIMARY KEY (idUser, idPublication, idModerationType) -- Llave primaria compuesta
);

CREATE TABLE Notification (
    idNotification INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT NOT NULL,
    typeNotification ENUM('Message', 'Like', 'Follow', 'Comment', 'Alert') NOT NULL,
    content VARCHAR(100),
	state ENUM('Read', 'Unread') NOT NULL,
    dateNotification TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE
);	

CREATE TABLE Comment (
    idComment INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT NOT NULL,
    idPublication INT NOT NULL,
    contentComment VARCHAR(200) NOT NULL,
    commentDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE,
    FOREIGN KEY (idPublication) REFERENCES Publication(idPublication) ON DELETE CASCADE
);

CREATE TABLE Reply (
    idReply INT PRIMARY KEY AUTO_INCREMENT,
    idUser INT NOT NULL,
    idComment INT NOT NULL,
    contentReply VARCHAR(255) NOT NULL,
    replyDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE,
    FOREIGN KEY (idComment) REFERENCES Comment(idComment) ON DELETE CASCADE
);

CREATE TABLE Reaction (
    idUser INT NOT NULL,
    idPublication INT NOT NULL,
    liked BOOLEAN NOT NULL, -- true: le gusta, false: no le gusta
    reactionDate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (idUser) REFERENCES User(idUser) ON DELETE CASCADE,
    FOREIGN KEY (idPublication) REFERENCES Publication(idPublication) ON DELETE CASCADE,
    UNIQUE (idUser, idPublication), -- Evita múltiples reacciones
    PRIMARY KEY (idUser, idPublication) -- Llave primaria compuesta
);

CREATE TABLE MentionedUser (
    idPublication INT NOT NULL,
    idMentionedUser INT NOT NULL,
    FOREIGN KEY (idPublication) REFERENCES Publication(idPublication) ON DELETE CASCADE,
    FOREIGN KEY (idMentionedUser) REFERENCES User(idUser) ON DELETE CASCADE,
    UNIQUE (idPublication, idMentionedUser), -- Evita menciones duplicadas
    PRIMARY KEY (idPublication, idMentionedUser) -- Llave primaria compuesta
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





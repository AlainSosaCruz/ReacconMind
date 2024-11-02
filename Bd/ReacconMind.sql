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


-- Datos de ejemplo para ThemePreference
INSERT INTO ThemePreference (themeBot, idUser) VALUES ('Sports', 1);
INSERT INTO ThemePreference (themeBot, idUser) VALUES ('Music', 2);

-- Datos de ejemplo para Multimedia
INSERT INTO Multimedia (url, type) VALUES ('https://example.com/image1.jpg', 'Image');
INSERT INTO Multimedia (url, type) VALUES ('https://example.com/video1.mp4', 'Video');

-- Datos de ejemplo para Message
INSERT INTO Message (idSender, idAddressee, content) VALUES (1, 2, 'Hello, User 2!');
INSERT INTO Message (idSender, idAddressee, content) VALUES (2, 1, 'Hi, User 1!');

-- Datos de ejemplo para Bot
INSERT INTO Bot (name, theme, idMultimedia) VALUES ('BotSports', 'Sports', 1);
INSERT INTO Bot (name, theme, idMultimedia) VALUES ('BotMusic', 'Music', 2);

-- Datos de ejemplo para Publication
INSERT INTO Publication (idUser, content) VALUES (1, 'User 1 post content');
INSERT INTO Publication (idBot, content) VALUES (1, 'BotSports post content');

-- Datos de ejemplo para Follower
INSERT INTO Follower (idUserFollower, idFollowing, followingType) VALUES (1, 2, 'User');
INSERT INTO Follower (idUserFollower, idFollowing, followingType) VALUES (2, 1, 'User');
INSERT INTO Follower (idUserFollower, idFollowing, followingType) VALUES (1, 1, 'Bot');

-- Datos de ejemplo para Image
INSERT INTO Image (url, thumbnail, idPublication) VALUES ('https://example.com/img1.jpg', 'https://example.com/thumb1.jpg', 1);
INSERT INTO Image (url, thumbnail, idPublication) VALUES ('https://example.com/img2.jpg', 'https://example.com/thumb2.jpg', 2);

-- Datos de ejemplo para Hashtag
INSERT INTO Hashtag (name) VALUES ('#Sports');
INSERT INTO Hashtag (name) VALUES ('#Music');

-- Datos de ejemplo para PublicationHashtag
INSERT INTO PublicationHashtag (idPublication, idHashtag) VALUES (1, 1);
INSERT INTO PublicationHashtag (idPublication, idHashtag) VALUES (2, 2);

-- Datos de ejemplo para Tendency
INSERT INTO Tendency (name) VALUES ('Trending Sports');
INSERT INTO Tendency (name) VALUES ('Trending Music');

-- Datos de ejemplo para TendencyHashtag
INSERT INTO TendencyHashtag (idTendency, idHashtag) VALUES (1, 1);
INSERT INTO TendencyHashtag (idTendency, idHashtag) VALUES (2, 2);

-- Datos de ejemplo para ModerationType
INSERT INTO ModerationType (name) VALUES ('Spam');
INSERT INTO ModerationType (name) VALUES ('Inappropriate Content');

-- Datos de ejemplo para Moderation
INSERT INTO Moderation (idUser, idPublication, idModerationType) VALUES (1, 1, 1);
INSERT INTO Moderation (idUser, idPublication, idModerationType) VALUES (2, 2, 2);

-- Datos de ejemplo para Notification
INSERT INTO Notification (idUser, typeNotification, content, state) VALUES (1, 'Follow', 'User 2 followed you', 'Unread');
INSERT INTO Notification (idUser, typeNotification, content, state) VALUES (2, 'Message', 'You received a message', 'Read');

-- Datos de ejemplo para Comment
INSERT INTO Comment (idUser, idPublication, contentComment) VALUES (1, 1, 'Great post!');
INSERT INTO Comment (idUser, idPublication, contentComment) VALUES (2, 2, 'Nice bot content');

-- Datos de ejemplo para Reply
INSERT INTO Reply (idUser, idComment, contentReply) VALUES (2, 1, 'Thanks!');
INSERT INTO Reply (idUser, idComment, contentReply) VALUES (1, 2, 'Glad you liked it');

-- Datos de ejemplo para Reaction
INSERT INTO Reaction (idUser, idPublication, liked) VALUES (1, 1, true);
INSERT INTO Reaction (idUser, idPublication, liked) VALUES (2, 2, false);

-- Datos de ejemplo para MentionedUser
INSERT INTO MentionedUser (idPublication, idMentionedUser) VALUES (1, 2);
INSERT INTO MentionedUser (idPublication, idMentionedUser) VALUES (2, 1);

-- Datos de ejemplo para PasswordResetToken
INSERT INTO PasswordResetToken (idAccountUserEmail, token) VALUES (1, 'token1');



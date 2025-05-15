CREATE DATABASE space_shooter;

USE space_shooter;

CREATE TABLE IF NOT EXISTS player(
    Username VARCHAR(15) PRIMARY KEY,
    Password VARCHAR(64) NOT NULL
);


CREATE TABLE IF NOT EXISTS highscore(
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Highscore INT NOT NULL,
    Username VARCHAR(15) NOT NULL,
    CONSTRAINT unique_username UNIQUE (Username)
);
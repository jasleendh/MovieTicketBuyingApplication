CREATE TABLE User_info(
userId BIGINT NOT NULL Primary Key AUTO_INCREMENT,
firstName VARCHAR(128),
lastName VARCHAR(128),
userName VARCHAR(128),
encryptedPassword VARCHAR(128) NOT NULL,
  ENABLED           BIT NOT NULL 
);

CREATE TABLE Role
(
  roleId   BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  roleName VARCHAR(30) NOT NULL UNIQUE
) ;

CREATE TABLE User_Role
(
  ID      BIGINT NOT NULL Primary Key AUTO_INCREMENT,
  userId BIGINT NOT NULL,
  roleId BIGINT NOT NULL
);

ALTER TABLE User_Role
  add constraint User_Role_UK unique (userId, roleId);

ALTER TABLE User_Role
  add constraint User_Role_FK1 foreign key (userId)
  references User_info (userId);
 
ALTER TABLE User_Role
  add constraint Role_FK2 foreign key (roleId)
  references Role (roleId);
  
  
   
INSERT INTO User_info (firstName, lastName, userName, encryptedPassword, ENABLED) VALUES
('Jasleen', 'Dhillon', 'jasleen1742', '$2a$10$S.fpCrEMLayQYusmpCDJBuZ7Xcnx.l6/rLG8kOXD.uxr0Gkg9f1w.', 1);

INSERT INTO User_info (firstName, lastName, userName, encryptedPassword, ENABLED) VALUES
('Wei', 'Zheming', 'wzm503', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

INSERT INTO User_info (firstName, lastName, userName, encryptedPassword, ENABLED) VALUES
('Shawn', 'Mendes', 'shawn22mendes', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

INSERT INTO User_info (firstName, lastName, userName, encryptedPassword, ENABLED) VALUES
('Camilla', 'Cabello', 'Camilla22', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

INSERT INTO User_info (firstName, lastName, userName, encryptedPassword, ENABLED) VALUES
('Diljit', 'Dosanjh', 'diljit2020', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

INSERT INTO User_info (firstName, lastName, userName, encryptedPassword, ENABLED) VALUES
('Kangana', 'Ranaut', 'Ranaut2020', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1); 

  
  
  
INSERT INTO Role (roleName)
VALUES ('ROLE_ADMIN');

INSERT INTO Role (roleName)
VALUES ('ROLE_USER');

 
INSERT INTO User_Role (userId, roleId)
VALUES (1, 1);
 
INSERT INTO User_Role (userId, roleId)
VALUES (1, 2);
 
INSERT INTO User_Role (userId, roleId)
VALUES (2, 2);

INSERT INTO User_Role (userId, roleId)
VALUES (3, 2);

INSERT INTO User_Role (userId, roleId)
VALUES (4, 2);

INSERT INTO User_Role (userId, roleId)
VALUES (5, 2);

INSERT INTO User_Role (userId, roleId)
VALUES (6, 2);
  
  

CREATE TABLE movies (
movieId BIGINT NOT NULL Primary Key AUTO_INCREMENT,
title VARCHAR(128),
movieDuration VARCHAR(50),
language VARCHAR(100),
showTime VARCHAR(50),
seatsAvailable INT(100)
);

INSERT INTO movies (title, movieDuration, language, showTime, seatsAvailable) VALUES
('Dolittle', '1h 41m', 'English', '6:30 pm', 25),
('The Hobbit 5', '2h 45m', 'English', '2:15 pm', 29),
('Golmaal Returns', '1h 55m', 'Hindi', '11:45 am', 42),
('Super Sonic', '1h 15m', 'English','5:35 pm', 26),
('Tom and Jerry', '1h 58m',  'English', '7:55 pm', 6),
('Manje Bistre 2', '2h 8m', 'Punjabi', '3:20 pm', 23),
('Boruto: Naruto Next Generation', '1h 35m',  'English', '10:00 am', 9),
('Boruto: Naruto Next Generation (original)', '1h 35m',  'Japanese (with subtitles in English)', '9:00 pm', 31),
('Naruto the movie', '1h 34m',  'English', '11:00 am', 6),
('Naruto the movie  (original)', '1h 34m',  'Japanese (with subtitles in English)', '7:55 pm', 6);

;


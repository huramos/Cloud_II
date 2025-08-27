-- Script inicial para base de datos Oracle
CREATE TABLE Roles (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(50),
    description VARCHAR2(255)
);

CREATE TABLE Users (
    id NUMBER PRIMARY KEY,
    name VARCHAR2(100),
    email VARCHAR2(100),
    role_id NUMBER,
    FOREIGN KEY (role_id) REFERENCES Roles(id)
);
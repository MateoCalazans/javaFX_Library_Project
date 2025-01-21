create database crud;
use crud;
create table students(
    id int primary key auto_increment,
    FirstName varchar(20) not null,
    LastName varchar(20) not null,
    COURSE varchar(20) not null

);
insert into students(FirstName, LastName, COURSE) values("Mateo","Duarte","JavaHARD");

CREATE TABLE books (
    id INT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    genre VARCHAR(50) NOT NULL
);


show databases;

create database sgcm;
use sgcm;

create table usuarios (
	id int not null,
    nome varchar(50),
    senha varchar(100),
    primary key(id)
);

show tables;

describe usuarios;

insert into usuarios(id, senha) values (1, "1234");

update usuarios set nome = 'João' where id = 1;

delete from usuarios where id = 1;

select * from usuarios;



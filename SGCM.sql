show databases;

create database sgcm;
use sgcm;

create table usuarios (
	id int not null,
    nome varchar(50),
    senha varchar(100),
    primary key (id)
);

show tables;
describe usuarios;

insert into usuarios (id, senha)
	values (1, '1234');
    
select * from usuarios;

update usuarios set nome = 'Tiago' where id = 1;

select * from usuarios;

delete from usuarios where id = 1;

select * from usuarios;
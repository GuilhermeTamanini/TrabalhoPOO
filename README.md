## Necessário o uso do xammp pra testar.

### Segue o domínio de problema. Desenvolva operações CRUD para instanciar objetos produtos em um SGBD relacional, cuja atributos: código, descrição e preço. Há produtos que são nacionais e produtos importados. O preço do produto importado tem acréscimo de 15% de imposto, enquanto que o nacional somente 8%. Para esse desenvolvimento explore Interface e tratamento de exceção, além da implementação das classes utilizando padrões POO em Java e a classe Scanner.

#01 Inicializar o BDR mariaDB (antigo MySQL) <br/>
mysql -u root

#02 Verificar se o mariaDB está funcionando<br/>
show databases;

#03 Criar um banco de dados<br/>
create database sgdb;

#04 Usar o banco 
use sgdb;

#05 Criar tabela do produto<br/>
create table product(<br/>
code int not null,<br/>
name varchar(50) not null,<br/>
description varchar(150) not null,<br/>
price float not null,<br/>
primary key(code)<br/>
);

#06 Verificar a estrutura da tabela<br/>
desc product;

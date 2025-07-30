create table topicos(
    id bigint not null auto_increment,
    titulo varchar(100) not null unique,
    mensaje varchar(255) not null unique,
    fecha datetime not null,
    id_autor bigint not null,
    status varchar(20) not null,

    primary key(id),
    constraint fk_autor foreign key(id_autor) references usuarios(id)
);
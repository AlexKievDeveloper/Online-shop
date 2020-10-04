create table users
(
    id       serial       not null
        constraint users_pk
            primary key,
    login    varchar(40)  not null,
    password varchar(200) not null,
    role     varchar(40)  not null,
    sole     varchar(200) not null
);

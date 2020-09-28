create table users
(
    id       serial       not null
        constraint users_pk
            primary key,
    login    varchar(40)  not null,
    password varchar(200) not null,
    role     role         not null
);

alter table users
    owner to postgres;

create unique index users_id_uindex
    on users (id);

create unique index users_login_uindex
    on users (login);
create type role as enum ('GUEST', 'USER', 'ADMIN');
create table users
(
    id       serial       not null
        constraint users_pk
            primary key,
    login    varchar(40)  not null,
    password varchar(200) not null,
    role     role         not null
);

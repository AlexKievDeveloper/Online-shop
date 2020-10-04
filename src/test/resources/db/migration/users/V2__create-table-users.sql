create table USERS
(
    id           INT auto_increment,
    login        VARCHAR(40) not null,
    password     VARCHAR(40) not null,
    sole         VARCHAR(200) not null,
    role         VARCHAR(40) not null,
        constraint USERS_PK
        primary key (ID)
);

create unique index USERS_ID_UINDEX
    on USERS (ID);
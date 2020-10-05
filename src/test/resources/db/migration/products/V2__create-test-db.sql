create table PRODUCTS
(
    id          INT auto_increment,
    name        VARCHAR(40)  not null,
    price       DOUBLE       not null,
    description VARCHAR(40)  not null,
    image       VARCHAR(200) not null,
    constraint PRODUCTS_PK
        primary key (id)
);

create unique index PRODUCTS_ID_UINDEX
    on PRODUCTS (id);
create index PRODUCTS_NAME_UINDEX
    on PRODUCTS (name);
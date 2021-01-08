create table PRODUCTS
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(40)  not null,
    price       DOUBLE       not null,
    description VARCHAR(40)  not null,
    image       VARCHAR(200) not null
);

create unique index PRODUCTS_ID_UINDEX
    on PRODUCTS (id);
create index PRODUCTS_NAME_UINDEX
    on PRODUCTS (name);



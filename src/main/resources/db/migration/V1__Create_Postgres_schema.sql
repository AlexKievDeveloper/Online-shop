create table products
(
    id          serial           not null
        constraint products_pk
            primary key,
    name        varchar(40)      not null,
    price       double precision not null,
    description varchar(40)      not null,
    image       bytea
);

alter table products
    owner to postgres;

create unique index products_id_uindex
    on products (id);
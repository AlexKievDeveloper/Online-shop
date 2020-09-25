create table products
(
    id          serial           not null
        constraint products_pk
            primary key,
    name        varchar(40)      not null,
    price       double precision not null,
    description varchar(40)      not null,
    image       varchar(200)     not null
);

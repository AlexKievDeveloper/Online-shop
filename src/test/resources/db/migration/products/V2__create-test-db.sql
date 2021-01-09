create table if not exists products
(
    id          serial           not null
        constraint products_pk
            primary key,
    name        varchar(40)      not null,
    price       double precision not null,
    description varchar(40)      not null,
    image       varchar(200)     not null
);

create index "product-id"
    on products (id);

create index "product-name"
    on products (name);



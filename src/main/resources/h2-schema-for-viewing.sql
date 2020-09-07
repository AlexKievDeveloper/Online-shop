create table IF NOT EXISTS PRODUCTS
(
    ID          INT auto_increment,
    NAME        VARCHAR(40) not null,
    PRICE       DOUBLE      not null,
    DESCRIPTION VARCHAR(40) not null,
    IMAGE       VARCHAR(40) not null,
    constraint PRODUCTS_PK
        primary key (ID)
);

create unique index IF NOT EXISTS PRODUCTS_ID_UINDEX
    on PRODUCTS (ID);

INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Victory-1765', 229.99, 'description',
                                                              '/img/victory-pen-300.jpg');
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('USS Glory-1941', 129.99, 'description',
                                                              '/img/carolina-300.jpg');
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Gettysburg-1863', 199.99, 'description',
                                                              '/img/gettinsburg-300.jpg');
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Last bullet', 189.99, 'description',
                                                              '/img/Pen-6-300.jpg');
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Glory to the king', 249.99, 'description',
                                                              '/img/Pen-7-300.jpg');
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Dragon scales', 299.99, 'description',
                                                              '/img/Pen-9-300.jpg');
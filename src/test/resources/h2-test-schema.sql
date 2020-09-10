create table PRODUCTS
(
    ID          INT auto_increment,
    NAME        VARCHAR(40) not null,
    PRICE       DOUBLE      not null,
    DESCRIPTION VARCHAR(40) not null,
    IMAGE       VARCHAR(200) not null,
    constraint PRODUCTS_PK
        primary key (ID)
);

create unique index PRODUCTS_ID_UINDEX
    on PRODUCTS (ID);

INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Victory-1765', 299.99, 'description',
                                                              'https://i.postimg.cc/J0993gLN/victory-pen-300.jpg');
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Victory-1765', 299.99, 'description',
                                                              'https://i.postimg.cc/J0993gLN/victory-pen-300.jpg');
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Victory-1765', 299.99, 'description',
                                                              'https://i.postimg.cc/J0993gLN/victory-pen-300.jpg');
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Victory-1765', 299.99, 'description',
                                                              'https://i.postimg.cc/J0993gLN/victory-pen-300.jpg');
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Victory-1765', 299.99, 'description',
                                                              'https://i.postimg.cc/J0993gLN/victory-pen-300.jpg');

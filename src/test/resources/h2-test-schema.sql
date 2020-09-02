create table PRODUCTS
(
    ID          INT auto_increment,
    NAME        VARCHAR(40) not null,
    PRICE       DOUBLE      not null,
    DESCRIPTION VARCHAR(40) not null,
    IMAGE       BYTEA       not null,
    constraint PRODUCTS_PK
        primary key (ID)
);

create unique index PRODUCTS_ID_UINDEX
    on PRODUCTS (ID);

INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Oil', 46.40, 'Brent Crude Oil',
                                                              FILE_READ('classpath:./oil.jpg'));
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Oil', 46.40, 'Brent Crude Oil',
                                                              FILE_READ('classpath:./oil.jpg'));
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Oil', 46.40, 'Brent Crude Oil',
                                                              FILE_READ('classpath:./oil.jpg'));
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Oil', 46.40, 'Brent Crude Oil',
                                                              FILE_READ('classpath:./oil.jpg'));
INSERT INTO PRODUCTS(NAME, PRICE, DESCRIPTION, IMAGE) VALUES ('Oil', 46.40, 'Brent Crude Oil',
                                                              FILE_READ('classpath:./oil.jpg'));
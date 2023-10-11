create table inventory(
    id int(19) primary key not null AUTO_INCREMENT,
    sku_code varchar(80) not null,
    quantity int(11) not null
);
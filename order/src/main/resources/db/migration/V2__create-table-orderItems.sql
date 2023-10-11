create table order_items(
        id varchar(80) primary key not null,
        sku_code varchar(255) not null,
        price decimal(19,2) not null,
        quantity int(11) not null,
        id_order varchar(80),
        foreign key (id_order) references orders(id)
);
create table ingredient (id bigint not null auto_increment, expiration_time integer, name varchar(255), uom varchar(255), category_id bigint, primary key (id)) engine=InnoDB
create table ingredient_category (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table menu (id varchar(255) not null, date date, primary key (id)) engine=InnoDB
create table menu_category (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table menu_option (id bigint not null auto_increment, actual_quantity integer, forecast_quantity integer, menu_option_type varchar(255), menu_id varchar(255), menu_category_id bigint, recipe_id bigint, primary key (id)) engine=InnoDB
create table recipe (id bigint not null auto_increment, cook_time integer, image longblob, name varchar(255), prep_time integer, servings integer, recipe_category_id bigint, primary key (id)) engine=InnoDB
create table recipe_category (id bigint not null auto_increment, description varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table recipe_ingredient (id bigint not null auto_increment, quantity integer, ingredient_id bigint, recipe_id bigint, primary key (id)) engine=InnoDB
create table stock (id bigint not null auto_increment, last_supply_date date, total integer, ingredient_id bigint, primary key (id)) engine=InnoDB
create table stock_entry (id bigint not null auto_increment, current_qty integer, expiration_date date, note varchar(255), original_qty integer, price decimal(19,2), supply_date date, stock_id bigint, primary key (id)) engine=InnoDB
create table user (id bigint not null auto_increment, enabled bit not null, password varchar(255), role varchar(255), username varchar(255) not null, primary key (id)) engine=InnoDB
alter table recipe_ingredient add constraint UK58ocuab26seiw3rx6gd3eh4dn unique (recipe_id, ingredient_id)
alter table stock add constraint UK45l03mb9d2qaymgdrub5m3rd4 unique (ingredient_id)
alter table user add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username)
alter table ingredient add constraint FKj4w6uwjhbjuuhprwsv5hvayyi foreign key (category_id) references ingredient_category (id)
alter table menu_option add constraint FKlr6b5i0f2uomfr8u2q3tt2q9f foreign key (menu_id) references menu (id)
alter table menu_option add constraint FK4jwofxyql6mo41yy0w0v8qov3 foreign key (menu_category_id) references menu_category (id)
alter table menu_option add constraint FK6w1dujuqa0sa4r2mmdqepkt0b foreign key (recipe_id) references recipe (id)
alter table recipe add constraint FKhvgma14wtuckyo4hriw9dn9v6 foreign key (recipe_category_id) references recipe_category (id)
alter table recipe_ingredient add constraint FK9b3oxoskt0chwqxge0cnlkc29 foreign key (ingredient_id) references ingredient (id)
alter table recipe_ingredient add constraint FKgu1oxq7mbcgkx5dah6o8geirh foreign key (recipe_id) references recipe (id)
alter table stock add constraint FK43aicdsfkxdb5nwihpe43bysx foreign key (ingredient_id) references ingredient (id)
alter table stock_entry add constraint FK71ampe7329f9xciqaiwfqqex6 foreign key (stock_id) references stock (id)

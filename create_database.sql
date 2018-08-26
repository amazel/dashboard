CREATE TABLE ingredient(
    id bigint NOT NULL auto_increment,
    expiration_time INTEGER,
    name            VARCHAR(255),
    uom             VARCHAR(255),
    category_id bigint,
    PRIMARY KEY (id)
  )
  engine=InnoDB;
CREATE TABLE ingredient_category(
    id bigint NOT NULL auto_increment,
    description VARCHAR(255),
    name        VARCHAR(255),
    PRIMARY KEY (id)
  )
  engine=InnoDB;
CREATE TABLE menu(
    id VARCHAR(255) NOT NULL,
       DATE DATE,
    PRIMARY KEY (id)
  )
  engine=InnoDB;
CREATE TABLE menu_category(
    id bigint NOT NULL auto_increment,
    description VARCHAR(255),
    name        VARCHAR(255),
    PRIMARY KEY (id)
  )
  engine=InnoDB;
CREATE TABLE menu_option(
    id bigint NOT NULL auto_increment,
    actual_quantity   INTEGER,
    forecast_quantity INTEGER,
    menu_option_type  VARCHAR(255),
    menu_id           VARCHAR(255),
    menu_category_id bigint,
    recipe_id bigint,
    PRIMARY KEY (id)
  )
  engine=InnoDB;
CREATE TABLE recipe(
    id bigint NOT NULL auto_increment,
    cook_time INTEGER,
    image longblob,
    name      VARCHAR(255),
    prep_time INTEGER,
    servings  INTEGER,
    recipe_category_id bigint,
    PRIMARY KEY (id)
  )
  engine=InnoDB;
CREATE TABLE recipe_category(
    id bigint NOT NULL auto_increment,
    description VARCHAR(255),
    name        VARCHAR(255),
    PRIMARY KEY (id)
  )
  engine=InnoDB;
CREATE TABLE recipe_ingredient(
    id bigint NOT NULL auto_increment,
    quantity INTEGER,
    ingredient_id bigint,
    recipe_id bigint,
    PRIMARY KEY (id)
  )
  engine=InnoDB;
CREATE TABLE stock(
    id bigint NOT NULL auto_increment,
    expiration_date  DATE,
    last_supply_date DATE,
    quantity         INTEGER,
    ingredient_id bigint,
    PRIMARY KEY (id)
  )
  engine=InnoDB;
ALTER TABLE recipe_ingredient ADD CONSTRAINT UK58ocuab26seiw3rx6gd3eh4dn UNIQUE (recipe_id, ingredient_id);
ALTER TABLE stock ADD CONSTRAINT UK45l03mb9d2qaymgdrub5m3rd4 UNIQUE (ingredient_id);
ALTER TABLE ingredient ADD CONSTRAINT FKj4w6uwjhbjuuhprwsv5hvayyi FOREIGN KEY (category_id) REFERENCES ingredient_category (id);
ALTER TABLE menu_option ADD CONSTRAINT FKlr6b5i0f2uomfr8u2q3tt2q9f FOREIGN KEY (menu_id) REFERENCES menu (id);
ALTER TABLE menu_option ADD CONSTRAINT FK4jwofxyql6mo41yy0w0v8qov3 FOREIGN KEY (menu_category_id) REFERENCES menu_category (id);
ALTER TABLE menu_option ADD CONSTRAINT FK6w1dujuqa0sa4r2mmdqepkt0b FOREIGN KEY (recipe_id) REFERENCES recipe (id);
ALTER TABLE recipe ADD CONSTRAINT FKhvgma14wtuckyo4hriw9dn9v6 FOREIGN KEY (recipe_category_id) REFERENCES recipe_category (id);
ALTER TABLE recipe_ingredient ADD CONSTRAINT FK9b3oxoskt0chwqxge0cnlkc29 FOREIGN KEY (ingredient_id) REFERENCES ingredient (id);
ALTER TABLE recipe_ingredient ADD CONSTRAINT FKgu1oxq7mbcgkx5dah6o8geirh FOREIGN KEY (recipe_id) REFERENCES recipe (id);
ALTER TABLE stock ADD CONSTRAINT FK43aicdsfkxdb5nwihpe43bysx FOREIGN KEY (ingredient_id) REFERENCES ingredient (id);

CREATE SEQUENCE  IF NOT EXISTS item_sequence START WITH 1 INCREMENT BY 500;

CREATE SEQUENCE  IF NOT EXISTS list_sequence START WITH 1 INCREMENT BY 500;

CREATE TABLE item (
  id BIGINT NOT NULL,
   version BIGINT,
   created_on BIGINT NOT NULL,
   updated_on BIGINT NOT NULL,
   product_id VARCHAR(255) NOT NULL,
   quantity INTEGER NOT NULL,
   shopping_list_id BIGINT,
   CONSTRAINT pk_item PRIMARY KEY (id)
);

CREATE TABLE shopping_list (
  id BIGINT NOT NULL,
   version BIGINT,
   created_on BIGINT NOT NULL,
   updated_on BIGINT NOT NULL,
   list_id VARCHAR(255) NOT NULL,
   name VARCHAR(255) NOT NULL,
   type VARCHAR(255) NOT NULL,
   CONSTRAINT pk_shopping_list PRIMARY KEY (id)
);

ALTER TABLE shopping_list ADD CONSTRAINT uc_shopping_list_listid UNIQUE (list_id);

CREATE INDEX index ON shopping_list(name, type);

CREATE INDEX uk_shoppingListId_N_productId_index ON item(shopping_list_id, product_id);

ALTER TABLE item ADD CONSTRAINT FK_ITEM_ON_SHOPPINGLISTID FOREIGN KEY (shopping_list_id) REFERENCES shopping_list (id);

CREATE INDEX fk_shopping_list_id_index ON item(shopping_list_id);
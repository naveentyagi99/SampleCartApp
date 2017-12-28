

CREATE TABLE CART (id INT AUTO_INCREMENT PRIMARY KEY CHECK(id > 0), name VARCHAR(20) CHECK(name > ''), createdAt DATETIME , updatedAt DATETIME);

INSERT INTO CART(id, name, createdAt, updatedAt)VALUES(1,'Cart1', '2017-12-17:10:48:58', '2017-12-17:11:48:58');
INSERT INTO CART(id, name, createdAt, updatedAt)VALUES(2,'Cart2', '2017-12-17:11:48:58', '2017-12-17:11:48:58');
INSERT INTO CART(id, name, createdAt, updatedAt)VALUES(3,'Cart3', '2017-12-17:12:48:58', '2017-12-17:11:48:58');

CREATE TABLE ITEM (
  id INT AUTO_INCREMENT PRIMARY KEY CHECK(id > 0),
  description varchar(20) CHECK(description > ''),
  createdAt TIMESTAMP, updatedAt TIMESTAMP,
  shoppingCartId INT
);
ALTER TABLE PUBLIC.ITEM ADD FOREIGN KEY ( shoppingCartId ) REFERENCES CART( id ) ON DELETE CASCADE ;

INSERT INTO ITEM(id, description, createdAt, updatedAt, shoppingCartId)VALUES(1,'Item1','2017-12-17:15:30:20','2017-12-17:17:30:20',1);
INSERT INTO ITEM(id, description, createdAt, updatedAt, shoppingCartId)VALUES(2,'Item2','2017-12-17:16:30:20','2017-12-17:17:30:20',1);
INSERT INTO ITEM(id, description, createdAt, updatedAt, shoppingCartId)VALUES(3,'Item3','2017-12-17:17:30:20','2017-12-17:17:30:20',2);



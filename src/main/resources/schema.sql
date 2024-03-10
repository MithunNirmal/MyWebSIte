CREATE TABLE image(
   image_id int,
   product_id varchar(36),
   link VARCHAR(200),
   PRIMARY KEY(image_id),
   CONSTRAINT fk_product
      FOREIGN KEY(product_id)
	  REFERENCES product(id)
	  ON DELETE CASCADE
);
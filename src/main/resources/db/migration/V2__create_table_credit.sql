CREATE TABLE credits (
  credit_code UUID NOT NULL,
   credit_value DECIMAL NOT NULL,
   day_first_installment date NOT NULL,
   number_of_installments INT NOT NULL,
   status SMALLINT NOT NULL,
   customer_id BIGINT,
   CONSTRAINT pk_credits PRIMARY KEY (credit_code)
);

ALTER TABLE credits ADD CONSTRAINT FK_CREDITS_ON_CUSTOMER FOREIGN KEY (customer_id) REFERENCES customers (id);
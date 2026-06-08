ALTER TABLE invoice
    ADD COLUMN invoice_number VARCHAR(255),
    ADD COLUMN vendor_name    VARCHAR(255),
    ADD COLUMN subtotal       DECIMAL(19, 2),
    ADD COLUMN tax            DECIMAL(19, 2),
    ADD COLUMN total          DECIMAL(19, 2),
    ADD COLUMN confidence     DOUBLE;

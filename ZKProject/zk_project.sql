CREATE DATABASE zktask;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE contact (
    id INT AUTO_INCREMENT PRIMARY KEY,
    status VARCHAR(255),
    company_name VARCHAR(255),
    short_name VARCHAR(255),
    created_by INT NOT NULL,
    created_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by INT NULL DEFAULT NULL,
    updated_on timestamp NULL DEFAULT NULL,
    FOREIGN KEY (created_by) REFERENCES user(id),
    FOREIGN KEY (updated_by) REFERENCES user(id)
);

CREATE TABLE category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE contact_category (
    contact_id INT  NOT NULL,
    category_id INT  NOT NULL,
    PRIMARY KEY (contact_id, category_id),
    FOREIGN KEY (contact_id) REFERENCES contact(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE contact_office (
    id INT AUTO_INCREMENT PRIMARY KEY,
    contact_id INT NOT NULL,
    office_name VARCHAR(255),
    address_1 VARCHAR(255),
    address_2 VARCHAR(255),
    town_city VARCHAR(255),
    postcode VARCHAR(255),
    country VARCHAR(255),
    telephone_1 VARCHAR(255),
    telephone_2 VARCHAR(255),
    fax VARCHAR(255),
    mobile VARCHAR(255),
    email VARCHAR(255),
    url VARCHAR(255),
    status VARCHAR(255),
    FOREIGN KEY (contact_id) REFERENCES contact(id)

);
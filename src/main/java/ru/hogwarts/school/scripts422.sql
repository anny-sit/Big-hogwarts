CREATE TABLE human (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    age INTEGER,
    hasLicence BOOLEAN
);

CREATE TABLE car (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    model VARCHAR(100),
    price REAL,
    owner_id INTEGER,
    FOREIGN KEY (owner_id) REFERENCES human(id)
);
BEGIN;

-- Table: user_entity
CREATE TABLE user_entity (
    id SERIAL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);

-- Table: movie_entity
CREATE TABLE movie_entity (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description VARCHAR(2500),
    length INT,
    director VARCHAR(255),
    premiere_year INT,
    categories TEXT[], -- Array of categories
    img_main VARCHAR(255)
);

-- Table: airing_entity
CREATE TABLE airing_entity (
    id SERIAL PRIMARY KEY,
    time TIMESTAMP NOT NULL,
    type INT NOT NULL, -- EnumType.ORDINAL is mapped to an integer
    movie_id INT REFERENCES movie_entity (id) ON DELETE CASCADE
);

-- Table: ticket_entity
CREATE TABLE ticket_entity (
    id SERIAL PRIMARY KEY,
    code VARCHAR(255) NOT NULL UNIQUE,
    airing_id INT REFERENCES airing_entity (id) ON DELETE CASCADE,
    status INT DEFAULT 2, -- EnumType.ORDINAL is mapped to an integer (2 = VALID by default)
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(50),
    seats_taken INT[] -- Array of seat numbers
);

-- Intermediate table for movie_entity -> airing_entity relationship
CREATE TABLE movie_entity_airings (
    movie_entity_id INT REFERENCES movie_entity (id) ON DELETE CASCADE,
    airings_id INT REFERENCES airing_entity (id) ON DELETE CASCADE,
    PRIMARY KEY (movie_entity_id, airings_id)
);

-- Intermediate table for airing_entity -> ticket_entity relationship
CREATE TABLE airing_entity_tickets (
    airing_entity_id INT REFERENCES airing_entity (id) ON DELETE CASCADE,
    tickets_id INT REFERENCES ticket_entity (id) ON DELETE CASCADE,
    PRIMARY KEY (airing_entity_id, tickets_id)
);

COMMIT;
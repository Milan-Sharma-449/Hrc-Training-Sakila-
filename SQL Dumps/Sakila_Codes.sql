# Allowing File Transfer
SET GLOBAL local_infile=1;

# Displaying Film Table
SELECT * FROM film;

# SQL Query to Check out Number of Columns of a Table
SELECT COUNT(*) AS Number_Of_Columns FROM INFORMATION_SCHEMA.COLUMNS
WHERE table_schema = 'sakila' AND TABLE_NAME = 'film';

# Checking out Number of Rows
SELECT COUNT(*) AS Number_Of_Rows FROM film;

# Selecting out Unique Languages
SELECT DISTINCT(language_id) FROM film;

# Using OFFSET and LIMIT
SELECT * FROM film LIMIT 99, 2;

# Displaying Film Table Joined with Language
SELECT 
film_data.film_id,
film_data.title,
film_data.description,
film_data.release_year,
lang.name AS `language`,
film_data.original_language_id,
film_data.rental_duration,
film_data.rental_rate,
film_data.length,
film_data.replacement_cost,
film_data.rating,
film_data.special_features,
film_data.last_update,
film_data.director
FROM film AS film_data
LEFT JOIN `language` AS lang ON film_data.language_id = lang.language_id;

# Taking out Unique Special Features from Film Table
SELECT DISTINCT(special_features) FROM film;

# Taking out Unique Special Features from Film Table
SELECT DISTINCT(rating) FROM film;

# Inserting into Film DB
INSERT INTO film (title, DESCRIPTION, release_year, language_id, director, rating, special_features)
VALUES ("Titanic", "Bekar", 2020, 1, "Rahul", "PG", "Trailers")

# Inserting into Language DB
INSERT INTO `language` (language_id, `name`)
VALUES (10, "Hindi");

# Testing out Update Statement
UPDATE film 
SET title = "Titanic PRO", 
`description` = "Aru Bekaaaaaaar"
WHERE film_id = 1003;

# Testing out Delete Statement
DELETE FROM film WHERE film_id = 1003;

# Testing out Search Statement
SELECT * FROM film 
WHERE
title LIKE "%Aca%"
# and director IS NULL
AND release_year = 2006
AND language_id = 1;

# Working out with Multiple Tables
SELECT * FROM film
WHERE language_id = (SELECT language_id FROM `language` WHERE NAME = "Hindi");

# Temp Area [Testing]

## Insertion Test
INSERT INTO film (title, `description`, release_year, language_id, director, rating, special_features)
VALUES ("Meow", "Cat", 2021, (SELECT language_id FROM `language` WHERE `name` = "Hindi"), "Rony", "G", "Deleted Scenes")

## Update Test
UPDATE film 
SET title = "Meow PRO", 
`description` = "Cat Hehe",
language_id = (SELECT language_id FROM `language` WHERE `name` = "French")
WHERE film_id = 1008;

## Delete Check
# Testing out Delete Statement
DELETE FROM film WHERE film_id = "1009";

# Altering Table for Hibernate
ALTER TABLE film DROP COLUMN isDeleted;
ALTER TABLE film MODIFY isDeleted BOOLEAN NOT NULL DEFAULT 0;
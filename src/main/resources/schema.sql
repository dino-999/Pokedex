CREATE TABLE IF NOT EXISTS pokemon (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         name VARCHAR(255),
                         height INT,
                         weight INT,
                         type VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS refresh_token (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         expiry_date TIMESTAMP NOT NULL,
                         token VARCHAR(255) NOT NULL,
                         username VARCHAR(255) NOT NULL
);

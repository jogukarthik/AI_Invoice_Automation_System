CREATE TABLE invoice (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    file_name VARCHAR(255),
    source_email VARCHAR(255),
    file_path VARCHAR(255),
    status VARCHAR(50),
    created_at TIMESTAMP
);

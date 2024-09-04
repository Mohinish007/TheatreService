-- Theatre Table
CREATE TABLE IF NOT EXISTS theatre (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

-- Movie Table
CREATE TABLE IF NOT EXISTS movie (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    language VARCHAR(50)
);

-- movie_show Table
CREATE TABLE IF NOT EXISTS `movie_show` (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    theatre_id BIGINT NOT NULL,
    movie_id BIGINT NOT NULL,
    show_time TIMESTAMP NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (theatre_id) REFERENCES theatre(id),
    FOREIGN KEY (movie_id) REFERENCES movie(id)
);

-- Seat Table
CREATE TABLE IF NOT EXISTS seat (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    show_id BIGINT NOT NULL,
    row_num VARCHAR(5) NOT NULL,
    column_num INT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    status ENUM('AVAILABLE', 'BOOKED', 'RESERVED') NOT NULL DEFAULT 'AVAILABLE',
    FOREIGN KEY (show_id) REFERENCES movie_show(id)
);

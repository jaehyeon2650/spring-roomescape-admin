CREATE TABLE reservation_time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id  BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) references reservation_time(id)
);
DROP TABLE IF EXISTS teams;

CREATE TABLE teams (
    team_id VARCHAR(50) PRIMARY KEY,
    team_name VARCHAR(255) NOT NULL,
    number_of_members INT NOT NULL
);
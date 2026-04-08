CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(80) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS career_fairs (
    id VARCHAR(80) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date_value VARCHAR(50) NOT NULL,
    time_value VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    companies_json TEXT NOT NULL,
    status VARCHAR(50) NOT NULL,
    registrations INTEGER NOT NULL,
    category VARCHAR(120) NOT NULL
);

CREATE TABLE IF NOT EXISTS booths (
    id VARCHAR(80) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    industry VARCHAR(255) NOT NULL,
    logo VARCHAR(50) NOT NULL,
    tagline TEXT NOT NULL,
    description TEXT NOT NULL,
    open_roles INTEGER NOT NULL,
    location VARCHAR(255) NOT NULL,
    type VARCHAR(120) NOT NULL,
    rating DOUBLE PRECISION NOT NULL,
    employees VARCHAR(120) NOT NULL,
    positions_json TEXT NOT NULL,
    perks_json TEXT NOT NULL,
    color_json TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS resumes (
    id VARCHAR(80) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    company VARCHAR(255) NOT NULL,
    summary TEXT NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    date_value VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS chat_messages (
    id VARCHAR(80) PRIMARY KEY,
    booth_id VARCHAR(80) NOT NULL,
    sender VARCHAR(255) NOT NULL,
    message TEXT NOT NULL,
    timestamp_value VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS registrations (
    id VARCHAR(80) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    target VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    registered_at VARCHAR(100) NOT NULL
);
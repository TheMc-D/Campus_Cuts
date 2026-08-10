CREATE TABLE schools (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    state VARCHAR(2) NOT NULL
);

CREATE TABLE campus_areas (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    school_id BIGINT NOT NULL REFERENCES schools (id),
    name VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    school_id BIGINT NOT NULL REFERENCES schools (id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE providers (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE REFERENCES users (id),
    campus_area_id BIGINT NOT NULL REFERENCES campus_areas (id),
    display_name VARCHAR(255) NOT NULL,
    bio TEXT,
    service_type VARCHAR(30) NOT NULL,
    location_detail VARCHAR(255),
    avg_rating NUMERIC(3, 2),
    rating_count INTEGER NOT NULL DEFAULT 0
);

CREATE TABLE services (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    provider_id BIGINT NOT NULL REFERENCES providers (id),
    name VARCHAR(255) NOT NULL,
    price NUMERIC(8, 2) NOT NULL,
    duration_minutes INTEGER NOT NULL
);

CREATE TABLE availabilities (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    provider_id BIGINT NOT NULL REFERENCES providers (id),
    day_of_week VARCHAR(10) NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL
);

CREATE TABLE conversations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    student_id BIGINT NOT NULL REFERENCES users (id),
    provider_id BIGINT NOT NULL REFERENCES providers (id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (student_id, provider_id)
);

CREATE TABLE messages (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    conversation_id BIGINT NOT NULL REFERENCES conversations (id),
    sender_id BIGINT NOT NULL REFERENCES users (id),
    body TEXT NOT NULL,
    sent_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP
);

CREATE TABLE reviews (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    provider_id BIGINT NOT NULL REFERENCES providers (id),
    author_id BIGINT NOT NULL REFERENCES users (id),
    rating INTEGER NOT NULL,
    body TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (author_id, provider_id)
);

CREATE TABLE favorites (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users (id),
    provider_id BIGINT NOT NULL REFERENCES providers (id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, provider_id)
);

CREATE TABLE reports (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    reporter_id BIGINT NOT NULL REFERENCES users (id),
    target_type VARCHAR(20) NOT NULL,
    target_id BIGINT NOT NULL,
    reason TEXT,
    status VARCHAR(20) NOT NULL DEFAULT 'OPEN',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reports_target ON reports (target_type, target_id);

-- V1__Create_link_table.sql
CREATE TABLE links (
    -- Campos da própria entidade Link
    id UUID NOT NULL PRIMARY KEY,
    full_url TEXT NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,

    -- Campos herdados da nossa classe Auditable
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_by VARCHAR(255) NOT NULL
);
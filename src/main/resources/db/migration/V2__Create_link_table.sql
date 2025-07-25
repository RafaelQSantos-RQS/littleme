-- V2__Create_link_table.sql
CREATE TABLE links (
    id UUID NOT NULL PRIMARY KEY,
    full_url TEXT NOT NULL,
    code VARCHAR(20) NOT NULL UNIQUE,
    description VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    deleted_at TIMESTAMP WITH TIME ZONE NULL,
    created_by UUID NOT NULL,
    updated_by UUID NOT NULL,
    deleted_by UUID NULL,
    CONSTRAINT fk_links_created_by FOREIGN KEY (created_by) REFERENCES users(id),
    CONSTRAINT fk_links_updated_by FOREIGN KEY (updated_by) REFERENCES users(id),
    CONSTRAINT fk_links_deleted_by FOREIGN KEY (deleted_by) REFERENCES users(id)
);
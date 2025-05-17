-- ===========================================
-- Script Name: database_setup.sql
-- Description: Master script for setting up
--              the tour_manager_db database.
-- ===========================================

-- STEP 1: Create the database
DO $$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'tour_manager_db') THEN
            CREATE DATABASE tour_manager_db;
        END IF;
    END
$$;

-- STEP 2: Switch to the database
\c tour_manager_db;

-- STEP 3: Execute the schema creation script
\i schema.sql

-- STEP 4: Execute the seed data script
\i seed_data.sql
-- ===========================================
-- Script Name: schema.sql
-- Description: Defines the schema for the tour_manager_db
--              (tables, constraints, keys, etc.)
-- ===========================================

-- STEP 1: Create the 'tours' table
CREATE TABLE IF NOT EXISTS tours (
                                        id UUID PRIMARY KEY,                      -- Primary key: unique ID for each tour
                                        name VARCHAR(255) NOT NULL,               -- Tour name
                                        description TEXT,                         -- A brief description of the tour
                                        "from" VARCHAR(255) NOT NULL,            -- Starting point of the tour
                                        "to" VARCHAR(255) NOT NULL,              -- Destination of the tour
                                        transport_type VARCHAR(100),             -- Type of transport (e.g., car, bike, train)
                                        distance DOUBLE PRECISION,               -- Travel distance in kilometers
                                        estimated_time DOUBLE PRECISION,         -- Estimated time in hours
                                        image_url TEXT,                           -- Image URL for the tour (optional)
                                        popularity INTEGER,
                                        child_friendliness DOUBLE PRECISION,
                                        startLat DOUBLE PRECISION,
                                        startLon DOUBLE PRECISION,
                                        endLat DOUBLE PRECISION,
                                        endLon DOUBLE PRECISION
);

-- STEP 2: Create the 'tour_logs' table
CREATE TABLE IF NOT EXISTS tour_logs (
                                         id UUID PRIMARY KEY,                       -- Primary key: unique ID for each log
                                         tour_id UUID NOT NULL,                     -- Foreign key referencing the 'tours' table
                                         date_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Log timestamp (default: current time)
                                         total_distance DOUBLE PRECISION,           -- Duration of this log entry in hours
                                         total_time DOUBLE PRECISION,
                                         rating INTEGER NOT NULL,
                                         comment TEXT,                             -- Additional comment about the trip or activity
                                         difficulty VARCHAR(255),                  -- Difficulty as a string
                                         FOREIGN KEY (tour_id) REFERENCES tours (id) ON DELETE CASCADE -- Maintain tour_logs-to-tours relation
);
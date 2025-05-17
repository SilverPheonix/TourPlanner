-- ===========================================
-- Script Name: seed_data.sql
-- Description: Inserts initial data for testing
--              and verifying schema integrity
-- ===========================================

-- Insert a sample tour into tours table
INSERT INTO tours (id, name, description, "from", "to", transport_type, distance, estimated_time, image_url)
VALUES (
           '550e8400-e29b-41d4-a716-446655440000',
           'Mountain Trail Adventure',
           'An exciting mountain hiking tour.',
           'Trail Start',
           'Trail End',
           'Hiking',
           12.5,
           5.0,
           'http://example.com/mountain_trail.jpg'
       );

-- Insert a sample tour log into tour_logs table
INSERT INTO tour_logs (id, tour_id, duration, comment, difficulty)
VALUES (
           '660e9500-e29c-51d5-b816-556655550001',
           '550e8400-e29b-41d4-a716-446655440000',
           5.0,
           'Completed with stunning views, but very challenging!',
           8
       );
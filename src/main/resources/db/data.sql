-- =============================================
-- FINAL SCRIPT - Users + Portfolios + Socials
-- Matches your current portfolios table structure
-- =============================================

ROLLBACK;  -- End any previous failed transaction

BEGIN;

-- Clear previous data safely
TRUNCATE TABLE users RESTART IDENTITY CASCADE;
TRUNCATE TABLE portfolios RESTART IDENTITY CASCADE;
TRUNCATE TABLE socials RESTART IDENTITY CASCADE;

-- ==================== USERS ====================
INSERT INTO users (id, email, image, is_verified, name, password, role) VALUES
    ('04bf1727-8c3b-4b28-ac0b-d66108f4e09e', 'john@mail.com', NULL, true, 'John', '$2a$10$ukoZOBFn7g8Bk1gyZuR.tOrk4CMetF3XVWZbvFq/WgiowsiVAQ3Ci', 'USER'),
    ('3a2478e6-1380-483d-897a-c400fe906769', 'jane@mail.com', NULL, true, 'Jane', '$2a$10$etKJbhOku4i/Ojwenb8wkOXTSB3fjxcivdTHSF1F6SnJB0l3UXJ0m', 'USER'),
    ('fb8b5972-70ef-47bf-b02c-8a04b58f4422', 'tomas@mail.com', NULL, true, 'Thomas', '$2a$10$wM4ISHoUe7uq/dtHKq4rQeDsAXyuLT0nXZ3Bxkc6aflva5C.TwlfG', 'USER'),
    ('0e0e1d27-7bee-4abc-9361-79cd774b9cc7', 'chris@mail.com', NULL, true, 'Chris', '$2a$10$OpT5W3/MepEe9WLYiASGh.ftYTyfI2YG5WumxN1C5o3Y3.XZWtPqC', 'USER'),
    ('a3053f4c-ad2c-4728-9660-bc4a3a482555', 'melinda@mail.com', NULL, true, 'Melinda', '$2a$10$mXWEd2/hRnNPNJDrVhKxS.qudi1nkq5wWWYbBG9NCl3rNfqcDsB3O', 'USER'),
    ('64c15cb8-7551-41dd-91e6-9a3fbb6b1839', 'claire@mail.com', NULL, true, 'Claire', '$2a$10$pRIzmNpBEEsDpcx/OJyBOOYBdAI6Xg7ETqv/eNBnFgf37JFCiFsUO', 'USER');

-- ==================== PORTFOLIOS ====================
INSERT INTO portfolios (
    id, user_id, badge_id, name, bio, details, email, phone, website,
    cover_image, address, city, country, latitude, longitude, status, created_at
) VALUES

-- Jane Rivera
('a1b2c3d4-e5f6-7890-abcd-1234567890aa', '3a2478e6-1380-483d-897a-c400fe906769', NULL,
 'Jane Rivera', 'Senior UI/UX Designer crafting delightful digital experiences',
 '{"ops":[{"insert":"Hi, I''m Jane Rivera, a passionate UI/UX Designer with 6+ years of experience creating intuitive and beautiful interfaces."},{"insert":"\n","attributes":{"header":2}},{"insert":"I specialize in turning complex problems into simple, beautiful designs that users love."}]}',
 'jane@mail.com', '+1 (555) 123-4567', 'https://janerivera.design',
 NULL, '123 Creative Street', 'San Francisco', 'United States', 37.7749, -122.4194, 'ACTIVE', NOW()),

-- Thomas Bennett
('a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'fb8b5972-70ef-47bf-b02c-8a04b58f4422', NULL,
 'Thomas Bennett', 'Full Stack Engineer | Building scalable web applications',
 '{"ops":[{"insert":"Hello! I''m Thomas, a passionate full-stack developer who loves clean code and great user experiences."},{"insert":"\n","attributes":{"header":2}},{"insert":"I specialize in Kotlin, Spring Boot, React, and PostgreSQL."}]}',
 'tomas@mail.com', '+1 (555) 234-5678', 'https://thomasbennett.dev',
 NULL, '456 Tech Avenue', 'Austin', 'United States', 30.2672, -97.7431, 'ACTIVE', NOW()),

-- Chris Morales
('a1b2c3d4-e5f6-7890-abcd-1234567890cc', '0e0e1d27-7bee-4abc-9361-79cd774b9cc7', NULL,
 'Chris Morales', 'Growth Marketing | Helping brands tell their story',
 '{"ops":[{"insert":"I help brands grow through strategic digital marketing and compelling storytelling."},{"insert":"\n","attributes":{"header":2}}]}',
 'chris@mail.com', '+1 (555) 345-6789', 'https://chrismorales.me',
 NULL, '789 Market Blvd', 'New York', 'United States', 40.7128, -74.0060, 'ACTIVE', NOW()),

-- Melinda Soto
('a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'a3053f4c-ad2c-4728-9660-bc4a3a482555', NULL,
 'Melinda Soto', 'Product Designer & Digital Illustrator',
 '{"ops":[{"insert":"Product designer with a love for illustration and thoughtful user interfaces."},{"insert":"\n","attributes":{"header":2}},{"insert":"I believe great products come from deep empathy and beautiful execution."}]}',
 'melinda@mail.com', '+1 (555) 456-7890', 'https://melindasoto.com',
 NULL, '321 Artist Lane', 'Portland', 'United States', 45.5231, -122.6765, 'ACTIVE', NOW()),

-- Claire Thompson
('a1b2c3d4-e5f6-7890-abcd-1234567890ee', '64c15cb8-7551-41dd-91e6-9a3fbb6b1839', NULL,
 'Claire Thompson', 'Senior Software Engineer & Technical Mentor',
 '{"ops":[{"insert":"Senior Software Engineer focused on backend systems and developer experience."},{"insert":"\n","attributes":{"header":2}},{"insert":"I enjoy building robust systems and helping other developers grow."}]}',
 'claire@mail.com', '+1 (555) 567-8901', 'https://clairethompson.dev',
 NULL, '654 Developer Road', 'Seattle', 'United States', 47.6062, -122.3321, 'ACTIVE', NOW());

-- ==================== SOCIALS ====================
INSERT INTO socials (id, portfolio_id, platform, url) VALUES
  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'LINKEDIN', 'https://linkedin.com/in/janerivera'),
  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'INSTAGRAM', 'https://instagram.com/janerivera.design'),
  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'DRIBBBLE', 'https://dribbble.com/janerivera'),

  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'GITHUB', 'https://github.com/thomasbennett'),
  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'LINKEDIN', 'https://linkedin.com/in/thomasbennett'),
  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'X', 'https://x.com/thomasbuilds'),

  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'LINKEDIN', 'https://linkedin.com/in/chrismorales'),
  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'INSTAGRAM', 'https://instagram.com/chrismarketing'),
  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'YOUTUBE', 'https://youtube.com/@chrismorales'),

  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'INSTAGRAM', 'https://instagram.com/melindasoto.art'),
  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'BEHANCE', 'https://behance.net/melindasoto'),
  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'LINKEDIN', 'https://linkedin.com/in/melindasoto'),

  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'GITHUB', 'https://github.com/clairethompson'),
  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'LINKEDIN', 'https://linkedin.com/in/clairethompson'),
  (gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'X', 'https://x.com/clairecodes');

COMMIT;

SELECT '✅ All users, portfolios, and socials inserted successfully!' AS status;
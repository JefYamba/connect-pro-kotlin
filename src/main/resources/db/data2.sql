BEGIN;

-- Clear previous data
TRUNCATE TABLE categories RESTART IDENTITY CASCADE;
TRUNCATE TABLE badges RESTART IDENTITY CASCADE;
TRUNCATE TABLE awards RESTART IDENTITY CASCADE;
TRUNCATE TABLE projects RESTART IDENTITY CASCADE;
TRUNCATE TABLE services RESTART IDENTITY CASCADE;
TRUNCATE TABLE job_posts RESTART IDENTITY CASCADE;
TRUNCATE TABLE service_tags RESTART IDENTITY CASCADE;
TRUNCATE TABLE job_post_tags RESTART IDENTITY CASCADE;

-- ==================== CATEGORIES ====================
INSERT INTO categories (id, name, created_at) VALUES
    (gen_random_uuid(), 'UI/UX Design', NOW()),
    (gen_random_uuid(), 'Web Development', NOW()),
    (gen_random_uuid(), 'Mobile Development', NOW()),
    (gen_random_uuid(), 'Digital Marketing', NOW()),
    (gen_random_uuid(), 'Graphic Design', NOW()),
    (gen_random_uuid(), 'Branding', NOW()),
    (gen_random_uuid(), 'Backend Development', NOW()),
    (gen_random_uuid(), 'Product Design', NOW()),
    (gen_random_uuid(), 'SEO & Content', NOW());

-- ==================== BADGES ====================
INSERT INTO badges (id, name, description, color) VALUES
    (gen_random_uuid(), 'Top Designer', 'Recognized for exceptional design quality', '#4F46E5'),
    (gen_random_uuid(), 'Rising Star', 'Fast growing professional', '#10B981'),
    (gen_random_uuid(), 'Expert Developer', 'Highly skilled software engineer', '#3B82F6'),
    (gen_random_uuid(), 'Marketing Guru', 'Proven marketing results', '#EC4899'),
    (gen_random_uuid(), 'Creative Genius', 'Outstanding creative work', '#F59E0B');

-- ==================== AWARDS ====================
INSERT INTO awards (id, name, description, color) VALUES
    (gen_random_uuid(), 'Best Service 2026', 'Highest rated service of the month', '#FFD700'),
    (gen_random_uuid(), 'Client Favorite', 'Most loved by clients', '#C026D3'),
    (gen_random_uuid(), 'Innovation Award', 'Most innovative solution', '#06B6D4'),
    (gen_random_uuid(), 'Reliability Badge', 'Consistently delivered on time', '#22C55E');

SELECT '✅ Data seeded successfully!' AS status;
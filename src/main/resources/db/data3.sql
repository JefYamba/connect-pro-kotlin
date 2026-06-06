/*
-- =============================================
-- RICH DEMO DATA - Projects, Services & Job Posts
-- For Jane, Thomas, Chris, Melinda, Claire
-- =============================================

BEGIN;

-- Clear previous demo data
TRUNCATE TABLE projects RESTART IDENTITY CASCADE;
TRUNCATE TABLE services RESTART IDENTITY CASCADE;
TRUNCATE TABLE job_posts RESTART IDENTITY CASCADE;

-- ==================== JANE RIVERA (UI/UX Designer) ====================

INSERT INTO projects (id, portfolio_id, name, description) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Fashion E-commerce App', '[{"insert":"Complete mobile app redesign for a luxury fashion brand with 50k+ monthly users."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Fintech Banking Dashboard', '[{"insert":"Modern SaaS banking platform with excellent user experience and accessibility focus."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'AI Productivity Tool', '[{"insert":"Landing page and user onboarding flow for an AI writing assistant."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Health & Wellness Platform', '[{"insert":"Mobile app for meditation and mental wellness tracking."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Real Estate Marketplace', '[{"insert":"Property listing platform with advanced filtering and 3D tours."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'SaaS Admin Dashboard', '[{"insert":"Internal tool redesign for a growing startup."},{"insert":"\n"}]');

INSERT INTO services (id, portfolio_id, category_id, name, description, status) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'UI/UX Design', '[{"insert":"End-to-end user interface and experience design"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'Mobile App Design', '[{"insert":"Native iOS & Android app design"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'Product Design' LIMIT 1), 'Product Design', '[{"insert":"Full product strategy and design systems"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'Branding' LIMIT 1), 'Brand Identity Design', '[{"insert":"Logo, visual identity and brand guidelines"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'Website Redesign', '[{"insert":"High-conversion website and landing page design"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'Graphic Design' LIMIT 1), 'Illustration & Icon Design', '[{"insert":"Custom illustrations and icon systems"},{"insert":"\n"}]', 'ACTIVE');

-- ==================== THOMAS BENNETT (Full Stack Developer) ====================

INSERT INTO projects (id, portfolio_id, name, description) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'ConnectPro Marketplace', 'Freelance marketplace platform built with Kotlin + Spring Boot + React.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'Real-time Inventory System', 'Warehouse management system with live tracking.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'Online Learning Platform', 'Full-stack education platform with video streaming.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'Job Board Application', 'Modern job board with matching algorithm.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'E-commerce Backend', 'High-performance backend for fashion store.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'Internal CRM System', 'Company management tool for small teams.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'Payment Gateway Integration', 'Multi-provider payment solution.');

INSERT INTO services (id, portfolio_id, category_id, name, description, status) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', (SELECT id FROM categories WHERE name = 'Web Development' LIMIT 1), 'Backend Development', 'Scalable Spring Boot microservices', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', (SELECT id FROM categories WHERE name = 'Backend Development' LIMIT 1), 'Full Stack Development', 'Complete web applications', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', (SELECT id FROM categories WHERE name = 'Mobile Development' LIMIT 1), 'API Development', 'REST & GraphQL APIs', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', (SELECT id FROM categories WHERE name = 'Web Development' LIMIT 1), 'System Architecture', 'High-performance system design', 'ACTIVE');

-- ==================== CHRIS MORALES (Digital Marketing) ====================

INSERT INTO projects (id, portfolio_id, name, description) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'Tech Startup SEO Campaign', 'Increased organic traffic by 340% in 6 months.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'E-commerce Google Ads', 'Managed 6-figure monthly ad spend with strong ROI.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'Social Media Strategy', 'Built viral campaigns for lifestyle brand.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'Content Marketing System', 'Automated content strategy for B2B SaaS.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'Email Marketing Automation', 'Nurturing sequences with 45% open rate.');

INSERT INTO services (id, portfolio_id, category_id, name, description, status) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', (SELECT id FROM categories WHERE name = 'SEO & Content' LIMIT 1), 'SEO Optimization', 'Technical and content SEO', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', (SELECT id FROM categories WHERE name = 'Digital Marketing' LIMIT 1), 'Google Ads Management', 'PPC campaign management', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', (SELECT id FROM categories WHERE name = 'Digital Marketing' LIMIT 1), 'Social Media Marketing', 'Platform strategy and content', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', (SELECT id FROM categories WHERE name = 'SEO & Content' LIMIT 1), 'Content Strategy', 'Long-form and viral content', 'ACTIVE');

-- ==================== MELINDA SOTO (Product Designer & Illustrator) ====================

INSERT INTO projects (id, portfolio_id, name, description) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'Neobank Mobile App', 'Complete product design and illustration system for a modern digital bank.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'Meditation & Wellness App', 'Calm and mindful mobile experience with custom illustrations.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'Fashion E-commerce Platform', 'High-end shopping experience with elegant UI.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'AI Creative Tool', 'Interface design for an AI-powered design assistant.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'Children Learning Platform', 'Playful and educational interface for kids.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'SaaS Productivity Dashboard', 'Clean and professional workspace design.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'Travel Experience App', 'Beautiful travel booking and discovery platform.');

INSERT INTO services (id, portfolio_id, category_id, name, description, status) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'Product Design' LIMIT 1), 'Product Design', 'End-to-end product strategy and design', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'UI/UX Design', 'Beautiful and intuitive user interfaces', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'Graphic Design' LIMIT 1), 'Digital Illustration', 'Custom illustrations and iconography', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'Branding' LIMIT 1), 'Brand Identity', 'Complete visual identity systems', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'Design Systems', 'Scalable and consistent design systems', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'Graphic Design' LIMIT 1), 'Icon Design', 'Custom icon sets and illustration packs', 'ACTIVE');

INSERT INTO job_posts (id, portfolio_id, category_id, name, description, job_type, work_mode, is_closed) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'Senior Product Designer', 'Looking for a senior designer to join our design team', 'FULL_TIME', 'HYBRID', false),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'Graphic Design' LIMIT 1), 'Illustrator for Children App', 'Need talented illustrator for educational content', 'FREELANCE', 'REMOTE', false);

-- ==================== CLAIRE THOMPSON (Senior Software Engineer) ====================

INSERT INTO projects (id, portfolio_id, name, description) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'ConnectPro Backend', 'High-performance freelance marketplace backend.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'Real-time Analytics Platform', 'Live dashboard with WebSocket integration.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'Payment Processing System', 'Secure multi-currency payment gateway.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'Microservices Migration', 'Migrated legacy monolith to microservices.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'Developer Portal', 'Internal tools and documentation platform.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'Event-Driven Architecture', 'Kafka-based notification system.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'AI Recommendation Engine', 'Backend for personalized suggestions.');

INSERT INTO services (id, portfolio_id, category_id, name, description, status) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name = 'Backend Development' LIMIT 1), 'Backend Development', 'Scalable and maintainable backend systems', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name = 'Web Development' LIMIT 1), 'Full Stack Development', 'Complete web solutions with Kotlin', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name = 'Backend Development' LIMIT 1), 'System Architecture', 'High-level system design and planning', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name = 'Backend Development' LIMIT 1), 'API Development', 'REST and GraphQL API design', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name = 'Web Development' LIMIT 1), 'Database Optimization', 'Performance tuning and query optimization', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name = 'Backend Development' LIMIT 1), 'Microservices', 'Design and implementation of microservices', 'ACTIVE');

INSERT INTO job_posts (id, portfolio_id, category_id, name, description, job_type, work_mode, is_closed) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name = 'Web Development' LIMIT 1), 'Senior Backend Engineer', 'Join our team to build scalable systems', 'FULL_TIME', 'REMOTE', false),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name = 'Backend Development' LIMIT 1), 'Java/Kotlin Developer', 'Looking for experienced backend developers', 'INTERNSHIP', 'HYBRID', false),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name = 'Web Development' LIMIT 1), 'System Architect', 'Help us design the next generation architecture', 'PART_TIME', 'REMOTE', false);

COMMIT;

SELECT '✅ Rich demo data inserted successfully!' AS status;
*/


-- =============================================
-- COMPLETE DEMO DATA - Projects, Services, Job Posts
-- Jane + Thomas + Chris + Melinda + Claire
-- All descriptions formatted for Flutter Quill
-- =============================================

ROLLBACK;

BEGIN;

-- Clear previous data
TRUNCATE TABLE projects RESTART IDENTITY CASCADE;
TRUNCATE TABLE services RESTART IDENTITY CASCADE;
TRUNCATE TABLE job_posts RESTART IDENTITY CASCADE;

-- ==================== JANE RIVERA ====================

INSERT INTO projects (id, portfolio_id, name, description) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Fashion E-commerce App', '[{"insert":"Complete mobile app redesign for a luxury fashion brand with 50k+ monthly users."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Fintech Banking Dashboard', '[{"insert":"Modern SaaS banking platform with excellent user experience and accessibility focus."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'AI Productivity Tool', '[{"insert":"Landing page and user onboarding flow for an AI writing assistant."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Health & Wellness Platform', '[{"insert":"Mobile app for meditation and mental wellness tracking."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Real Estate Marketplace', '[{"insert":"Property listing platform with advanced filtering and 3D tours."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'SaaS Admin Dashboard', '[{"insert":"Internal tool redesign for a growing startup."},{"insert":"\n"}]');

INSERT INTO services (id, portfolio_id, category_id, name, description, status) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'UI/UX Design', '[{"insert":"End-to-end user interface and experience design"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'Mobile App Design', '[{"insert":"Native iOS & Android app design"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'Product Design' LIMIT 1), 'Product Design', '[{"insert":"Full product strategy and design systems"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'Branding' LIMIT 1), 'Brand Identity Design', '[{"insert":"Logo, visual identity and brand guidelines"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'Website Redesign', '[{"insert":"High-conversion website and landing page design"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'Graphic Design' LIMIT 1), 'Illustration & Icon Design', '[{"insert":"Custom illustrations and icon systems"},{"insert":"\n"}]', 'ACTIVE');

INSERT INTO job_posts (id, portfolio_id, category_id, name, description, job_type, work_mode, is_closed) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name ILIKE '%UI%' LIMIT 1), 'Senior UI/UX Designer', '[{"insert":"We are looking for a talented Senior UI/UX Designer to join our product team."},{"insert":"\n"}]', 'FULL_TIME', 'HYBRID', false),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name ILIKE '%Design%' LIMIT 1), 'Product Designer (Contract)', '[{"insert":"6-month contract for a major mobile product redesign."},{"insert":"\n"}]', 'INTERNSHIP', 'REMOTE', false);

-- ==================== THOMAS BENNETT ====================

INSERT INTO projects (id, portfolio_id, name, description) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'ConnectPro Marketplace', '[{"insert":"Freelance marketplace platform built with Kotlin + Spring Boot + React."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'Real-time Inventory System', '[{"insert":"Warehouse management system with live tracking and notifications."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'Online Learning Platform', '[{"insert":"Full-stack education platform with video streaming and progress tracking."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'Job Board Application', '[{"insert":"Modern job board with intelligent matching algorithm."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'E-commerce Backend', '[{"insert":"High-performance backend for a large fashion e-commerce store."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'Internal CRM System', '[{"insert":"Company management tool for small and medium teams."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', 'Payment Gateway Integration', '[{"insert":"Multi-provider secure payment solution."},{"insert":"\n"]}');

INSERT INTO services (id, portfolio_id, category_id, name, description, status) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', (SELECT id FROM categories WHERE name ILIKE '%Backend%' LIMIT 1), 'Backend Development', '[{"insert":"Scalable Spring Boot microservices and APIs"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', (SELECT id FROM categories WHERE name ILIKE '%Web%' LIMIT 1), 'Full Stack Development', '[{"insert":"Complete modern web applications with Kotlin and React"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', (SELECT id FROM categories WHERE name ILIKE '%Mobile%' LIMIT 1), 'API Development', '[{"insert":"REST & GraphQL API design and implementation"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', (SELECT id FROM categories WHERE name ILIKE '%Backend%' LIMIT 1), 'System Architecture', '[{"insert":"High-performance system design and optimization"},{"insert":"\n"}]', 'ACTIVE');

INSERT INTO job_posts (id, portfolio_id, category_id, name, description, job_type, work_mode, is_closed) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', (SELECT id FROM categories WHERE name ILIKE '%Web%' LIMIT 1), 'Senior Backend Engineer', '[{"insert":"Looking for an experienced Kotlin/Spring Boot developer"},{"insert":"\n"}]', 'FULL_TIME', 'REMOTE', false),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890bb', (SELECT id FROM categories WHERE name ILIKE '%Backend%' LIMIT 1), 'Full Stack Developer', '[{"insert":"Join our team to build the next generation marketplace"},{"insert":"\n"}]', 'FULL_TIME', 'HYBRID', false);

-- ==================== CHRIS MORALES ====================

INSERT INTO projects (id, portfolio_id, name, description) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'Tech Startup SEO Campaign', '[{"insert":"Increased organic traffic by 340% in 6 months."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'E-commerce Google Ads', '[{"insert":"Managed 6-figure monthly ad spend with strong ROI."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'Social Media Strategy', '[{"insert":"Built viral campaigns for lifestyle brand."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'Content Marketing System', '[{"insert":"Automated content strategy for B2B SaaS."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', 'Email Marketing Automation', '[{"insert":"Nurturing sequences with 45% open rate."},{"insert":"\n"]}');

INSERT INTO services (id, portfolio_id, category_id, name, description, status) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', (SELECT id FROM categories WHERE name ILIKE '%SEO%' LIMIT 1), 'SEO Optimization', '[{"insert":"Technical and content SEO optimization"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', (SELECT id FROM categories WHERE name ILIKE '%Marketing%' LIMIT 1), 'Google Ads Management', '[{"insert":"PPC campaign management and optimization"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', (SELECT id FROM categories WHERE name ILIKE '%Marketing%' LIMIT 1), 'Social Media Marketing', '[{"insert":"Platform strategy and content creation"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', (SELECT id FROM categories WHERE name ILIKE '%SEO%' LIMIT 1), 'Content Strategy', '[{"insert":"Long-form and viral content planning"},{"insert":"\n"}]', 'ACTIVE');

INSERT INTO job_posts (id, portfolio_id, category_id, name, description, job_type, work_mode, is_closed) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890cc', (SELECT id FROM categories WHERE name ILIKE '%Marketing%' LIMIT 1), 'Digital Marketing Manager', '[{"insert":"Looking for an experienced marketer to lead growth efforts"},{"insert":"\n"}]', 'FULL_TIME', 'REMOTE', false);

-- ==================== MELINDA SOTO ====================

INSERT INTO projects (id, portfolio_id, name, description) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'Neobank Mobile App', '[{"insert":"Complete product design and illustration system for a modern digital bank."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'Meditation & Wellness App', '[{"insert":"Calm and mindful mobile experience with custom illustrations."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'Fashion E-commerce Platform', '[{"insert":"High-end shopping experience with elegant UI."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'AI Creative Tool', '[{"insert":"Interface design for an AI-powered design assistant."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'Children Learning Platform', '[{"insert":"Playful and educational interface for kids."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'SaaS Productivity Dashboard', '[{"insert":"Clean and professional workspace design."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', 'Travel Experience App', '[{"insert":"Beautiful travel booking and discovery platform."},{"insert":"\n"}]');

INSERT INTO services (id, portfolio_id, category_id, name, description, status) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'Product Design' LIMIT 1), 'Product Design', '[{"insert":"End-to-end product strategy and design"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'UI/UX Design', '[{"insert":"Beautiful and intuitive user interfaces"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'Graphic Design' LIMIT 1), 'Digital Illustration', '[{"insert":"Custom illustrations and iconography"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'Branding' LIMIT 1), 'Brand Identity', '[{"insert":"Complete visual identity systems"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'Design Systems', '[{"insert":"Scalable and consistent design systems"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name = 'Graphic Design' LIMIT 1), 'Icon Design', '[{"insert":"Custom icon sets and illustration packs"},{"insert":"\n"}]', 'ACTIVE');

INSERT INTO job_posts (id, portfolio_id, category_id, name, description, job_type, work_mode, is_closed) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name ILIKE '%Design%' LIMIT 1), 'Senior Product Designer', '[{"insert":"Looking for a senior designer to join our design team"},{"insert":"\n"}]', 'FULL_TIME', 'HYBRID', false),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890dd', (SELECT id FROM categories WHERE name ILIKE '%Graphic%' LIMIT 1), 'Illustrator for Children App', '[{"insert":"Need talented illustrator for educational content"},{"insert":"\n"}]', 'FREELANCE', 'REMOTE', false);

-- ==================== CLAIRE THOMPSON ====================

INSERT INTO projects (id, portfolio_id, name, description) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'ConnectPro Backend', '[{"insert":"High-performance freelance marketplace backend."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'Real-time Analytics Platform', '[{"insert":"Live dashboard with WebSocket integration."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'Payment Processing System', '[{"insert":"Secure multi-currency payment gateway."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'Microservices Migration', '[{"insert":"Migrated legacy monolith to microservices."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'Developer Portal', '[{"insert":"Internal tools and documentation platform."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'Event-Driven Architecture', '[{"insert":"Kafka-based notification system."},{"insert":"\n"}]'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', 'AI Recommendation Engine', '[{"insert":"Backend for personalized suggestions."},{"insert":"\n"]}');

INSERT INTO services (id, portfolio_id, category_id, name, description, status) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name ILIKE '%Backend%' LIMIT 1), 'Backend Development', '[{"insert":"Scalable and maintainable backend systems"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name ILIKE '%Web%' LIMIT 1), 'Full Stack Development', '[{"insert":"Complete web solutions with Kotlin"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name ILIKE '%Backend%' LIMIT 1), 'System Architecture', '[{"insert":"High-level system design and planning"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name ILIKE '%Backend%' LIMIT 1), 'API Development', '[{"insert":"REST and GraphQL API design"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name ILIKE '%Web%' LIMIT 1), 'Database Optimization', '[{"insert":"Performance tuning and query optimization"},{"insert":"\n"}]', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name ILIKE '%Backend%' LIMIT 1), 'Microservices', '[{"insert":"Design and implementation of microservices"},{"insert":"\n"}]', 'ACTIVE');

INSERT INTO job_posts (id, portfolio_id, category_id, name, description, job_type, work_mode, is_closed) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name ILIKE '%Web%' LIMIT 1), 'Senior Backend Engineer', '[{"insert":"Join our team to build scalable systems"},{"insert":"\n"}]', 'FULL_TIME', 'REMOTE', false),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name ILIKE '%Backend%' LIMIT 1), 'Java/Kotlin Developer', '[{"insert":"Looking for experienced backend developers"},{"insert":"\n"}]', 'FULL_TIME', 'HYBRID', false),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890ee', (SELECT id FROM categories WHERE name ILIKE '%Web%' LIMIT 1), 'System Architect', '[{"insert":"Help us design the next generation architecture"},{"insert":"\n"}]', 'INTERNSHIP', 'REMOTE', false);

COMMIT;

SELECT '✅ All data for Jane, Thomas, Chris, Melinda and Claire inserted successfully!' AS status;
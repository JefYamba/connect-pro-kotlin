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
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Fashion E-commerce App', 'Complete mobile app redesign for a luxury fashion brand with 50k+ monthly users.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Fintech Banking Dashboard', 'Modern SaaS banking platform with excellent user experience and accessibility focus.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'AI Productivity Tool', 'Landing page and user onboarding flow for an AI writing assistant.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Health & Wellness Platform', 'Mobile app for meditation and mental wellness tracking.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'Real Estate Marketplace', 'Property listing platform with advanced filtering and 3D tours.'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', 'SaaS Admin Dashboard', 'Internal tool redesign for a growing startup.');

INSERT INTO services (id, portfolio_id, category_id, name, description, status) VALUES
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'UI/UX Design', 'End-to-end user interface and experience design', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'Mobile App Design', 'Native iOS & Android app design', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'Product Design' LIMIT 1), 'Product Design', 'Full product strategy and design systems', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'Branding' LIMIT 1), 'Brand Identity Design', 'Logo, visual identity and brand guidelines', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'UI/UX Design' LIMIT 1), 'Website Redesign', 'High-conversion website and landing page design', 'ACTIVE'),
(gen_random_uuid(), 'a1b2c3d4-e5f6-7890-abcd-1234567890aa', (SELECT id FROM categories WHERE name = 'Graphic Design' LIMIT 1), 'Illustration & Icon Design', 'Custom illustrations and icon systems', 'ACTIVE');

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
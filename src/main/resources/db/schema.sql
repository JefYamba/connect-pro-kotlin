-- users

CREATE TABLE users (
   id          UUID         NOT NULL PRIMARY KEY,
   email       VARCHAR(255),
   image       VARCHAR(255),
   is_verified BOOLEAN      NOT NULL,
   name        VARCHAR(255),
   password    VARCHAR(255),
   role        VARCHAR(10),

   CONSTRAINT chk_users_role CHECK (role IN ('USER', 'ADMIN'))
);


-- tokens

CREATE TABLE tokens (
    id           UUID                      NOT NULL PRIMARY KEY,
    otp_code     VARCHAR(10),
    created_at   TIMESTAMP(6) WITH TIME ZONE,
    expire_at    TIMESTAMP(6) WITH TIME ZONE,
    validated_at TIMESTAMP(6) WITH TIME ZONE,
    user_id      UUID,

    CONSTRAINT fk_tokens_user FOREIGN KEY (user_id) REFERENCES users (id)
);


-- categories

CREATE TABLE categories (
    id         UUID                      NOT NULL PRIMARY KEY,
    created_at TIMESTAMP(6) WITH TIME ZONE,
    name       VARCHAR(255)
);


-- tags

CREATE TABLE tags (
      id   UUID         NOT NULL PRIMARY KEY,
      name VARCHAR(255) NOT NULL
);


-- awards

CREATE TABLE awards (
    id          UUID         NOT NULL PRIMARY KEY,
    color       VARCHAR(20),
    description VARCHAR(255),
    name        VARCHAR(255)
);


-- badges

CREATE TABLE badges (
    id          UUID         NOT NULL PRIMARY KEY,
    color       VARCHAR(20),
    description VARCHAR(255),
    name        VARCHAR(255)
);


-- portfolios

CREATE TABLE portfolios (
    id           UUID                      NOT NULL PRIMARY KEY,
    user_id      UUID,
    badge_id     UUID,
    name         VARCHAR(180)              NOT NULL,
    bio          VARCHAR(255)              NOT NULL,
    details      TEXT,
    email        VARCHAR(255),
    phone        VARCHAR(50),
    website      VARCHAR(255),
    cover_image  VARCHAR(255),
    address      VARCHAR(160),
    city         VARCHAR(100)              NOT NULL,
    country      VARCHAR(100)              NOT NULL,
    latitude     DOUBLE PRECISION,
    longitude    DOUBLE PRECISION,
    status       VARCHAR(15),
    created_at   TIMESTAMP(6) WITH TIME ZONE,

    CONSTRAINT fk_portfolios_user    FOREIGN KEY (user_id)  REFERENCES users  (id),
    CONSTRAINT fk_portfolios_badge   FOREIGN KEY (badge_id) REFERENCES badges (id),
    CONSTRAINT chk_portfolios_status CHECK (status IN ('ACTIVE', 'INACTIVE', 'BLOCKED'))
);


-- socials

CREATE TABLE socials (
     id           UUID         NOT NULL PRIMARY KEY,
     portfolio_id UUID         NOT NULL,
     platform     VARCHAR(50),
     url          VARCHAR(255),

     CONSTRAINT fk_socials_portfolio          FOREIGN KEY (portfolio_id) REFERENCES portfolios (id),
     CONSTRAINT uq_socials_portfolio_platform UNIQUE (portfolio_id, platform),
     CONSTRAINT chk_socials_platform          CHECK (platform IN (
         'LINKEDIN', 'X', 'FACEBOOK', 'INSTAGRAM', 'YOUTUBE', 'GITHUB', 'DRIBBBLE', 'BEHANCE', 'WHATSAPP', 'TELEGRAM'))
);


-- projects

CREATE TABLE projects (
    id           UUID         NOT NULL PRIMARY KEY,
    portfolio_id UUID,
    name         VARCHAR(500),
    description  TEXT,
    
    CONSTRAINT fk_projects_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolios (id)
);


-- project_images

CREATE TABLE project_images (
    project_id UUID         NOT NULL,
    image      VARCHAR(255),
    
    CONSTRAINT fk_project_images_project FOREIGN KEY (project_id) REFERENCES projects (id)
);


-- services

CREATE TABLE services (
    id           UUID         NOT NULL PRIMARY KEY,
    portfolio_id UUID,
    category_id  UUID,
    award_id     UUID,
    name         VARCHAR(255) NOT NULL,
    description  TEXT        NOT NULL,
    cover_image  VARCHAR(255),
    status       VARCHAR(255),
    
    CONSTRAINT fk_services_portfolio FOREIGN KEY (portfolio_id) REFERENCES portfolios  (id),
    CONSTRAINT fk_services_category  FOREIGN KEY (category_id)  REFERENCES categories  (id),
    CONSTRAINT fk_services_award     FOREIGN KEY (award_id)     REFERENCES awards      (id),
    CONSTRAINT chk_services_status   CHECK (status IN ('ACTIVE', 'INACTIVE'))
);


-- service_images

CREATE TABLE service_images (
    service_id UUID         NOT NULL,
    image      VARCHAR(255),

    CONSTRAINT fk_service_images_service  FOREIGN KEY (service_id) REFERENCES services (id)
);


-- faqs

CREATE TABLE faqs (
    id         UUID         NOT NULL PRIMARY KEY,
    service_id UUID         NOT NULL,
    question   VARCHAR(255) NOT NULL,
    answer     VARCHAR(255) NOT NULL,
    
    CONSTRAINT fk_faqs_service FOREIGN KEY (service_id) REFERENCES services (id)
);


-- service_tags

CREATE TABLE service_tags (
    service_id UUID NOT NULL,
    tag_id     UUID NOT NULL,
    
    CONSTRAINT pk_service_tags          PRIMARY KEY (service_id, tag_id),
    CONSTRAINT fk_service_tags_service  FOREIGN KEY (service_id) REFERENCES services (id),
    CONSTRAINT fk_service_tags_tag      FOREIGN KEY (tag_id)     REFERENCES tags     (id)
);


-- likes

CREATE TABLE likes (
    service_id UUID                      NOT NULL,
    user_id    UUID                      NOT NULL,
    created_at TIMESTAMP(6) WITH TIME ZONE,
    
    CONSTRAINT pk_likes         PRIMARY KEY (service_id, user_id),
    CONSTRAINT fk_likes_service FOREIGN KEY (service_id) REFERENCES services (id),
    CONSTRAINT fk_likes_user    FOREIGN KEY (user_id)    REFERENCES users    (id)
);


-- reviews

CREATE TABLE reviews (
    reviewer_id UUID                      NOT NULL,
    service_id  UUID                      NOT NULL,
    comment     TEXT                      NOT NULL,
    rating      INTEGER,
    created_at  TIMESTAMP(6) WITH TIME ZONE,
    
    CONSTRAINT pk_reviews          PRIMARY KEY (reviewer_id, service_id),
    CONSTRAINT fk_reviews_reviewer FOREIGN KEY (reviewer_id) REFERENCES users    (id),
    CONSTRAINT fk_reviews_service  FOREIGN KEY (service_id)  REFERENCES services (id),
    CONSTRAINT chk_reviews_rating   CHECK (rating >= 1 AND rating <= 5)
);


-- job_posts

CREATE TABLE job_posts (
    id           UUID         NOT NULL PRIMARY KEY,
    portfolio_id UUID,
    category_id  UUID,
    name         VARCHAR(255),
    description  VARCHAR(255),
    job_type     VARCHAR(30),
    work_mode    VARCHAR(30),
    is_closed    BOOLEAN      NOT NULL,
    
    CONSTRAINT fk_job_posts_portfolio  FOREIGN KEY (portfolio_id) REFERENCES portfolios (id),
    CONSTRAINT fk_job_posts_category   FOREIGN KEY (category_id)  REFERENCES categories (id),
    CONSTRAINT chk_job_posts_job_type  CHECK (job_type  IN ('FULL_TIME', 'PART_TIME', 'FREELANCE', 'INTERNSHIP')),
    CONSTRAINT chk_job_posts_work_mode CHECK (work_mode IN ('REMOTE', 'ON_SITE', 'HYBRID'))
);


-- job_post_tags

CREATE TABLE job_post_tags (
    job_post_id UUID NOT NULL,
    tag_id      UUID NOT NULL,
    
    CONSTRAINT pk_job_post_tags          PRIMARY KEY (job_post_id, tag_id),
    CONSTRAINT fk_job_post_tags_job_post FOREIGN KEY (job_post_id) REFERENCES job_posts (id),
    CONSTRAINT fk_job_post_tags_tag      FOREIGN KEY (tag_id)      REFERENCES tags      (id)
);


-- job_applications

CREATE TABLE job_applications (
    applicant_id UUID                      NOT NULL,
    job_post_id  UUID                      NOT NULL,
    motivation   TEXT,
    status       VARCHAR(255),
    applied_at   TIMESTAMP(6) WITH TIME ZONE,
    
    CONSTRAINT pk_job_applications           PRIMARY KEY (applicant_id, job_post_id),
    CONSTRAINT fk_job_applications_applicant FOREIGN KEY (applicant_id) REFERENCES users     (id),
    CONSTRAINT fk_job_applications_job_post  FOREIGN KEY (job_post_id)  REFERENCES job_posts (id),
    CONSTRAINT chk_job_applications_status   CHECK (status IN ('PENDING', 'ACCEPTED', 'REJECTED'))
);


-- conversations

CREATE TABLE conversations (
    id               UUID                      NOT NULL PRIMARY KEY,
    participant_a    UUID,
    participant_b    UUID,
    created_at       TIMESTAMP(6) WITH TIME ZONE,
    last_modified_at TIMESTAMP(6) WITH TIME ZONE,
    
    CONSTRAINT fk_conversations_participant_a FOREIGN KEY (participant_a) REFERENCES users (id),
    CONSTRAINT fk_conversations_participant_b FOREIGN KEY (participant_b) REFERENCES users (id)
);


-- messages

CREATE TABLE messages (
    id              UUID                        NOT NULL PRIMARY KEY,
    conversation_id UUID,
    sender_id       UUID,
    receiver_id     UUID,
    content         TEXT                        NOT NULL,
    is_read         BOOLEAN                     NOT NULL,
    sent_at         TIMESTAMP(6) WITH TIME ZONE,
    
    CONSTRAINT fk_messages_conversation FOREIGN KEY (conversation_id) REFERENCES conversations (id),
    CONSTRAINT fk_messages_sender       FOREIGN KEY (sender_id)       REFERENCES users         (id),
    CONSTRAINT fk_messages_receiver     FOREIGN KEY (receiver_id)     REFERENCES users         (id)
);


-- events_tracking

CREATE TABLE events_tracking (
    id          UUID                        NOT NULL PRIMARY KEY,
    user_id     UUID,
    target_id   UUID,
    mata_data   TEXT,
    event_type  VARCHAR(255),
    target_type VARCHAR(255),
    timestamp   TIMESTAMP(6) WITH TIME ZONE,
    
    CONSTRAINT fk_events_tracking_user         FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT chk_events_tracking_event_type  CHECK (event_type  IN ('LIKE', 'REVIEW', 'CLICK', 'VIEW')),
    CONSTRAINT chk_events_tracking_target_type CHECK (target_type IN (
        'LIKE', 'REVIEW', 'PORTFOLIO', 'SERVICE', 'CONTACT_FOR_SERVICE', 'JOB_POST', 'JOB_APPLICATION'))
);


-- featured_services

CREATE TABLE featured_services (
    id         UUID                      NOT NULL PRIMARY KEY,
    service_id UUID,
    priority   INTEGER,
    source     varchar(20),
    start_at   TIMESTAMP(6) WITH TIME ZONE,
    end_at     TIMESTAMP(6) WITH TIME ZONE,
    created_at TIMESTAMP(6) WITH TIME ZONE,
    updated_at TIMESTAMP(6) WITH TIME ZONE,
    
    CONSTRAINT fk_featured_services_service FOREIGN KEY (service_id) REFERENCES services (id),
    CONSTRAINT chk_featured_services_source CHECK (source in ('RECOMMENDATION', 'PAID', 'ADMIN'))
);


-- featured_job_posts

CREATE TABLE featured_job_posts (
    id          UUID                      NOT NULL PRIMARY KEY,
    job_post_id UUID,
    priority    INTEGER,
    source      varchar(20),
    start_at    TIMESTAMP(6) WITH TIME ZONE,
    end_at      TIMESTAMP(6) WITH TIME ZONE,
    created_at  TIMESTAMP(6) WITH TIME ZONE,
    updated_at  TIMESTAMP(6) WITH TIME ZONE,

    CONSTRAINT fk_featured_job_posts_job_post FOREIGN KEY (job_post_id) REFERENCES job_posts (id),
    CONSTRAINT chk_featured_job_posts_source  CHECK (source in ('RECOMMENDATION', 'PAID', 'ADMIN'))
);

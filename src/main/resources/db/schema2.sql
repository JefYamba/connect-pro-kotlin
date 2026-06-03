create table awards
(
    id          uuid not null
        primary key,
    color       varchar(255),
    description varchar(255),
    name        varchar(255)
);

alter table awards
    owner to "connect-pro";

create table badges
(
    id          uuid not null
        primary key,
    color       varchar(255),
    description varchar(255),
    name        varchar(255)
);

alter table badges
    owner to "connect-pro";

create table categories
(
    id         uuid not null
        primary key,
    created_at timestamp(6) with time zone,
    name       varchar(255)
);

alter table categories
    owner to "connect-pro";

create table conversations
(
    id               uuid not null
        primary key,
    created_at       timestamp(6) with time zone,
    last_modified_at timestamp(6) with time zone,
    participant_a    uuid,
    participant_b    uuid
);

alter table conversations
    owner to "connect-pro";

create table events_tracking
(
    id          uuid not null
        primary key,
    event_type  varchar(255)
        constraint events_tracking_event_type_check
            check ((event_type)::text = ANY ((ARRAY ['LIKE'::character varying, 'REVIEW'::character varying, 'CLICK'::character varying, 'VIEW'::character varying])::text[])),
    target_id   uuid,
    target_type varchar(255)
        constraint events_tracking_target_type_check
            check ((target_type)::text = ANY
                   ((ARRAY ['LIKE'::character varying, 'REVIEW'::character varying, 'PORTFOLIO'::character varying, 'SERVICE'::character varying, 'CONTACT_FOR_SERVICE'::character varying, 'JOB_POST'::character varying, 'JOB_APPLICATION'::character varying])::text[])),
    timestamp   timestamp(6) with time zone,
    user_id     uuid
);

alter table events_tracking
    owner to "connect-pro";

create table featured_job_posts
(
    least_sig_bits bigint not null,
    most_sig_bits  bigint not null,
    created_at     timestamp(6) with time zone,
    end_at         timestamp(6) with time zone,
    job_post_id    uuid,
    priority       integer,
    source         varchar(255)
        constraint featured_job_posts_source_check
            check ((source)::text = ANY ((ARRAY ['RECOMMENDATION'::character varying, 'PAID'::character varying, 'ADMIN'::character varying])::text[])),
    start_at       timestamp(6) with time zone,
    updated_at     timestamp(6) with time zone,
    primary key (least_sig_bits, most_sig_bits)
);

alter table featured_job_posts
    owner to "connect-pro";

create table featured_services
(
    least_sig_bits bigint not null,
    most_sig_bits  bigint not null,
    created_at     timestamp(6) with time zone,
    end_at         timestamp(6) with time zone,
    priority       integer,
    service_id     uuid,
    source         varchar(255)
        constraint featured_services_source_check
            check ((source)::text = ANY ((ARRAY ['RECOMMENDATION'::character varying, 'PAID'::character varying, 'ADMIN'::character varying])::text[])),
    start_at       timestamp(6) with time zone,
    updated_at     timestamp(6) with time zone,
    primary key (least_sig_bits, most_sig_bits)
);

alter table featured_services
    owner to "connect-pro";

create table job_applications
(
    applicant_id uuid not null,
    job_post_id  uuid not null,
    applied_at   timestamp(6) with time zone,
    motivation   text,
    status       varchar(255)
        constraint job_applications_status_check
            check ((status)::text = ANY ((ARRAY ['PENDING'::character varying, 'ACCEPTED'::character varying, 'REJECTED'::character varying])::text[])),
    primary key (applicant_id, job_post_id)
);

alter table job_applications
    owner to "connect-pro";

create table job_posts
(
    id           uuid    not null
        primary key,
    category_id  uuid,
    description  varchar(255),
    is_closed    boolean not null,
    job_type     varchar(255)
        constraint job_posts_job_type_check
            check ((job_type)::text = ANY ((ARRAY ['FULL_TIME'::character varying, 'PART_TIME'::character varying, 'FREELANCE'::character varying, 'INTERNSHIP'::character varying])::text[])),
    name         varchar(255),
    portfolio_id uuid,
    work_mode    varchar(255)
        constraint job_posts_work_mode_check
            check ((work_mode)::text = ANY ((ARRAY ['REMOTE'::character varying, 'ON_SITE'::character varying, 'HYBRID'::character varying])::text[]))
);

alter table job_posts
    owner to "connect-pro";

create table likes
(
    service_id uuid not null,
    user_id    uuid not null,
    created_at timestamp(6) with time zone,
    primary key (service_id, user_id)
);

alter table likes
    owner to "connect-pro";

create table messages
(
    id              uuid    not null
        primary key,
    content         text    not null,
    conversation_id uuid,
    is_read         boolean not null,
    receiver_id     uuid,
    sender_id       uuid,
    sent_at         timestamp(6) with time zone
);

alter table messages
    owner to "connect-pro";

create table portfolios
(
    id          uuid         not null
        primary key,
    badge_id    uuid,
    bio         varchar(255) not null,
    email       varchar(255),
    phone       varchar(255),
    website     varchar(255),
    cover_image varchar(255),
    created_at  timestamp(6) with time zone,
    details     text,
    address     varchar(255),
    city        varchar(255) not null,
    country     varchar(255) not null,
    latitude    double precision,
    longitude   double precision,
    name        varchar(255) not null,
    status      varchar(255)
        constraint portfolios_status_check
            check ((status)::text = ANY ((ARRAY ['ACTIVE'::character varying, 'INACTIVE'::character varying, 'BLOCKED'::character varying])::text[])),
    user_id     uuid
);

alter table portfolios
    owner to "connect-pro";

create table projects
(
    id           uuid not null
        primary key,
    description  text,
    name         varchar(500),
    portfolio_id uuid
);

alter table projects
    owner to "connect-pro";

create table project_images
(
    project_id uuid not null
        constraint fkoej10untas4roy2rqxcmbdj42
            references projects,
    image      varchar(255)
);

alter table project_images
    owner to "connect-pro";

create table reviews
(
    reviewer_id uuid not null,
    service_id  uuid not null,
    comment     text not null,
    created_at  timestamp(6) with time zone,
    rating      integer,
    primary key (reviewer_id, service_id)
);

alter table reviews
    owner to "connect-pro";

create table services
(
    id           uuid         not null
        primary key,
    award_id     uuid,
    category_id  uuid,
    cover_image  varchar(255),
    description  text         not null,
    name         varchar(255) not null,
    portfolio_id uuid,
    status       varchar(255)
        constraint services_status_check
            check ((status)::text = ANY ((ARRAY ['ACTIVE'::character varying, 'INACTIVE'::character varying])::text[]))
    );

alter table services
    owner to "connect-pro";

create table faqs
(
    id         uuid         not null
        primary key,
    answer     varchar(255) not null,
    question   varchar(255) not null,
    service_id uuid         not null
        constraint fk9lv4i0b5hk5qby0fv5tmpq8ue
            references services
);

alter table faqs
    owner to "connect-pro";

create table service_images
(
    service_id uuid not null
        constraint fkico1fuyxgk2m2tfqt12tj4kh2
            references services,
    image      varchar(255)
);

alter table service_images
    owner to "connect-pro";

create table socials
(
    id           uuid not null
        primary key,
    platform     varchar(255)
        constraint socials_platform_check
            check ((platform)::text = ANY
        ((ARRAY ['LINKEDIN'::character varying, 'X'::character varying, 'FACEBOOK'::character varying, 'INSTAGRAM'::character varying, 'YOUTUBE'::character varying, 'GITHUB'::character varying, 'DRIBBBLE'::character varying, 'BEHANCE'::character varying, 'WHATSAPP'::character varying, 'TELEGRAM'::character varying])::text[])),
    url          varchar(255),
    portfolio_id uuid not null
        constraint fkr89s5ag77g35t2ljggll5glon
            references portfolios,
    constraint uk760ioldpk3kxf15eok922jm29
        unique (portfolio_id, platform)
);

alter table socials
    owner to "connect-pro";

create table tags
(
    id   uuid         not null
        primary key,
    name varchar(255) not null
);

alter table tags
    owner to "connect-pro";

create table job_post_tags
(
    job_post_id uuid not null
        constraint fkixet52i2s07e21c0n7t7nlrf4
            references job_posts,
    tag_id      uuid not null
        constraint fkn6w1qh8a94egisgkxtwa7j24x
            references tags,
    primary key (job_post_id, tag_id)
);

alter table job_post_tags
    owner to "connect-pro";

create table service_tags
(
    service_id uuid not null
        constraint fk5rgpqhfek0nrwelwflseobo2r
            references services,
    tag_id     uuid not null
        constraint fksjs3tml2h0g0gmxrar0lhqat4
            references tags,
    primary key (service_id, tag_id)
);

alter table service_tags
    owner to "connect-pro";

create table tokens
(
    id           uuid not null
        primary key,
    otp_code     varchar(255),
    created_at   timestamp(6) with time zone,
    expire_at    timestamp(6) with time zone,
    user_id      uuid,
    validated_at timestamp(6) with time zone
);

alter table tokens
    owner to "connect-pro";

create table users
(
    id          uuid    not null
        primary key,
    email       varchar(255),
    image       varchar(255),
    is_verified boolean not null,
    name        varchar(255),
    password    varchar(255),
    role        varchar(255)
        constraint users_role_check
            check ((role)::text = ANY ((ARRAY ['USER'::character varying, 'ADMIN'::character varying])::text[]))
    );

alter table users
    owner to "connect-pro";


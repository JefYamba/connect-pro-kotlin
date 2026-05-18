create table public.awards
(
    id          uuid not null
        primary key,
    color       varchar(255),
    description varchar(255),
    name        varchar(255)
);

alter table public.awards owner to "connect-pro";

create table public.badges
(
    id          uuid not null
        primary key,
    color       varchar(255),
    description varchar(255),
    name        varchar(255)
);

alter table public.badges  owner to "connect-pro";

create table public.categories
(
    created_at timestamp(6) with time zone,
    id         uuid not null
        primary key,
    name       varchar(255)
);

alter table public.categories  owner to "connect-pro";

create table public.conversations
(
    created_at       timestamp(6) with time zone,
    last_modified_at timestamp(6) with time zone,
    id               uuid not null
        primary key,
    participant_a    uuid,
    participant_b    uuid
);

alter table public.conversations   owner to "connect-pro";

create table public.events_tracking
(
    timestamp   timestamp(6) with time zone,
    id          uuid not null
        primary key,
    target_id   uuid,
    user_id     uuid,
    event_type  varchar(255)
        constraint events_tracking_event_type_check
            check ((event_type)::text = ANY
                   ((ARRAY ['LIKE'::character varying, 'REVIEW'::character varying, 'CLICK'::character varying, 'VIEW'::character varying])::text[])),
    target_type varchar(255)
        constraint events_tracking_target_type_check
            check ((target_type)::text = ANY
                   ((ARRAY ['LIKE'::character varying, 'REVIEW'::character varying, 'PORTFOLIO'::character varying, 'SERVICE'::character varying, 'CONTACT_FOR_SERVICE'::character varying, 'JOB_POST'::character varying, 'JOB_APPLICATION'::character varying])::text[]))
);

alter table public.events_tracking  owner to "connect-pro";

create table public.featured_job_posts
(
    priority    integer,
    source      smallint
        constraint featured_job_posts_source_check
            check ((source >= 0) AND (source <= 2)),
    created_at  timestamp(6) with time zone,
    end_at      timestamp(6) with time zone,
    start_at    timestamp(6) with time zone,
    updated_at  timestamp(6) with time zone,
    id          uuid not null
        primary key,
    job_post_id uuid
);

alter table public.featured_job_posts   owner to "connect-pro";

create table public.featured_services
(
    priority   integer,
    source     smallint
        constraint featured_services_source_check
            check ((source >= 0) AND (source <= 2)),
    created_at timestamp(6) with time zone,
    end_at     timestamp(6) with time zone,
    start_at   timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,
    id         uuid not null
        primary key,
    service_id uuid
);

alter table public.featured_services  owner to "connect-pro";

create table public.job_applications
(
    applied_at   timestamp(6) with time zone,
    applicant_id uuid not null,
    job_post_id  uuid not null,
    motivation   varchar(255),
    status       varchar(255)
        constraint job_applications_status_check
            check ((status)::text = ANY
                   ((ARRAY ['PENDING'::character varying, 'ACCEPTED'::character varying, 'REJECTED'::character varying])::text[])),
    primary key (applicant_id, job_post_id)
);

alter table public.job_applications  owner to "connect-pro";

create table public.job_posts
(
    amount_from      numeric(38, 2),
    amount_to        numeric(38, 2),
    deadline         date,
    is_closed        boolean not null,
    is_negociable    boolean,
    category_id      uuid,
    id               uuid    not null
        primary key,
    portfolio_id     uuid,
    description      varchar(255),
    job_type         varchar(255)
        constraint job_posts_job_type_check
            check ((job_type)::text = ANY
                   ((ARRAY ['FULL_TIME'::character varying, 'PART_TIME'::character varying, 'FREELANCE'::character varying, 'INTERNSHIP'::character varying])::text[])),
    pay_period       varchar(255)
        constraint job_posts_pay_period_check
            check ((pay_period)::text = ANY
                   ((ARRAY ['HOURLY'::character varying, 'DAILY'::character varying, 'WEEKLY'::character varying, 'MONTHLY'::character varying])::text[])),
    spoken_languages varchar(255),
    title            varchar(255),
    work_mode        varchar(255)
        constraint job_posts_work_mode_check
            check ((work_mode)::text = ANY
                   ((ARRAY ['REMOTE'::character varying, 'ON_SITE'::character varying, 'HYBRID'::character varying])::text[]))
);

alter table public.job_posts   owner to "connect-pro";

create table public.likes
(
    created_at timestamp(6) with time zone,
    service_id uuid not null,
    user_id    uuid not null,
    primary key (service_id, user_id)
);

alter table public.likes   owner to "connect-pro";

create table public.messages
(
    is_read         boolean not null,
    sent_at         timestamp(6) with time zone,
    conversation_id uuid,
    id              uuid    not null
        primary key,
    receiver_id     uuid,
    sender_id       uuid,
    content         varchar(255)
);

alter table public.messages owner to "connect-pro";

create table public.portfolios
(
    active_years        integer,
    latitude            double precision,
    longitude           double precision,
    number_of_employees integer,
    created_at          timestamp(6) with time zone,
    badge_id            uuid,
    id                  uuid         not null
        primary key,
    user_id             uuid,
    address             varchar(255),
    availabilities      text,
    city                varchar(255) not null,
    country             varchar(255) not null,
    cover_image         varchar(255),
    email               varchar(255),
    long_description    text,
    name                varchar(255) not null,
    phone1              varchar(255) not null,
    phone2              varchar(255),
    short_description   varchar(255) not null,
    spoken_languages    text,
    status              varchar(255)
        constraint portfolios_status_check
            check ((status)::text = ANY
                   ((ARRAY ['ACTIVE'::character varying, 'INACTIVE'::character varying, 'BLOCKED'::character varying])::text[])),
    type                varchar(255)
        constraint portfolios_type_check
            check ((type)::text = ANY
                   ((ARRAY ['INDIVIDUAL'::character varying, 'COMPANY'::character varying])::text[])),
    website_url         varchar(255)
);

alter table public.portfolios   owner to "connect-pro";

create table public.projects
(
    completed_at date,
    start_at     date,
    id           uuid not null
        primary key,
    portfolio_id uuid,
    title        varchar(500),
    description  text,
    images       text
);

alter table public.projects   owner to "connect-pro";

create table public.reviews
(
    rating      integer,
    created_at  timestamp(6) with time zone,
    reviewer_id uuid not null,
    service_id  uuid not null,
    comment     text not null,
    primary key (reviewer_id, service_id)
);

alter table public.reviews  owner to "connect-pro";

create table public.services
(
    award_id     uuid,
    category_id  uuid,
    id           uuid         not null
        primary key,
    portfolio_id uuid,
    cover_image  varchar(255),
    description  varchar(255) not null,
    images       text,
    pricing      text,
    status       varchar(255)
        constraint services_status_check
            check ((status)::text = ANY ((ARRAY ['ACTIVE'::character varying, 'INACTIVE'::character varying])::text[])),
    title        varchar(255) not null
);

alter table public.services  owner to "connect-pro";

create table public.faqs
(
    id         uuid         not null
        primary key,
    service_id uuid         not null
        constraint fk9lv4i0b5hk5qby0fv5tmpq8ue
            references public.services,
    answer     varchar(255) not null,
    question   varchar(255) not null
);

alter table public.faqs   owner to "connect-pro";

create table public.social_links
(
    id           uuid         not null
        primary key,
    portfolio_id uuid         not null
        constraint fks10393g3na130k18cgkcqrhnq
            references public.portfolios,
    name         varchar(255) not null,
    platform     varchar(255)
        constraint social_links_platform_check
            check ((platform)::text = ANY
                   ((ARRAY ['LINKEDIN'::character varying, 'X'::character varying, 'FACEBOOK'::character varying, 'INSTAGRAM'::character varying, 'YOUTUBE'::character varying, 'GITHUB'::character varying, 'DRIBBBLE'::character varying, 'BEHANCE'::character varying, 'WHATSAPP'::character varying, 'TELEGRAM'::character varying])::text[])),
    url          varchar(255)
        unique,
    unique (portfolio_id, platform)
);

alter table public.social_links  owner to "connect-pro";

create table public.tags
(
    id   uuid not null
        primary key,
    name varchar(255)
);

alter table public.tags   owner to "connect-pro";

create table public.job_post_tags
(
    job_post_id uuid not null
        constraint fkixet52i2s07e21c0n7t7nlrf4
            references public.job_posts,
    tag_id      uuid not null
        constraint fkn6w1qh8a94egisgkxtwa7j24x
            references public.tags,
    primary key (job_post_id, tag_id)
);

alter table public.job_post_tags  owner to "connect-pro";

create table public.service_tags
(
    service_id uuid not null
        constraint fk5rgpqhfek0nrwelwflseobo2r
            references public.services,
    tag_id     uuid not null
        constraint fksjs3tml2h0g0gmxrar0lhqat4
            references public.tags,
    primary key (service_id, tag_id)
);

alter table public.service_tags  owner to "connect-pro";

create table public.tokens
(
    created_at   timestamp(6) with time zone,
    expire_at    timestamp(6) with time zone,
    validated_at timestamp(6) with time zone,
    id           uuid not null
        primary key,
    user_id      uuid,
    otp_code     varchar(255)
);

alter table public.tokens   owner to "connect-pro";

create table public.users
(
    is_verified boolean not null,
    id          uuid    not null
        primary key,
    email       varchar(255),
    image       varchar(255),
    name        varchar(255),
    password    varchar(255),
    role        varchar(255)
        constraint users_role_check
            check ((role)::text = ANY ((ARRAY ['USER'::character varying, 'ADMIN'::character varying])::text[]))
);

alter table public.users  owner to "connect-pro";


-- Tables sans dépendances
create table public.awards
(
    id          uuid         not null primary key,
    color       varchar(255),
    description varchar(255),
    name        varchar(255)
);


create table public.badges
(
    id          uuid         not null primary key,
    color       varchar(255),
    description varchar(255),
    name        varchar(255)
);


create table public.categories
(
    id         uuid not null primary key,
    name       varchar(255),
    created_at timestamp(6) with time zone
);


create table public.tags
(
    id   uuid not null primary key,
    name varchar(255)
);


create table public.users
(
    id          uuid    not null primary key,
    email       varchar(255),
    name        varchar(255),
    password    varchar(255),
    image       varchar(255),
    role        varchar(255),
    is_verified boolean not null,

    constraint users_role_check
        check (role in ('USER', 'ADMIN'))
);


create table public.conversations
(
    id               uuid not null primary key,
    participant_a    uuid,
    participant_b    uuid,
    created_at       timestamp(6) with time zone,
    last_modified_at timestamp(6) with time zone
);


-- Tables dépendant de users
create table public.tokens
(
    id           uuid not null primary key,
    user_id      uuid,
    otp_code     varchar(255),
    created_at   timestamp(6) with time zone,
    expire_at    timestamp(6) with time zone,
    validated_at timestamp(6) with time zone,

    constraint fk_tokens_user
        foreign key (user_id) references public.users
);


-- Tables dépendant de conversations
create table public.messages
(
    id              uuid    not null primary key,
    conversation_id uuid    not null,
    sender_id       uuid    not null,
    receiver_id     uuid    not null,
    content         text    not null,
    is_read         boolean not null,
    sent_at         timestamp(6) with time zone,

    constraint fk_messages_conversation
        foreign key (conversation_id) references public.conversations
);


-- Tables dépendant de badges
create table public.portfolios
(
    id                  uuid         not null primary key,
    user_id             uuid,
    badge_id            uuid,
    name                varchar(255) not null,
    short_description   varchar(255) not null,
    long_description    text,
    city                varchar(255) not null,
    country             varchar(255) not null,
    address             varchar(255),
    phone1              varchar(255) not null,
    phone2              varchar(255),
    email               varchar(255),
    website_url         varchar(255),
    cover_image         varchar(255),
    spoken_languages    text,
    availabilities      text,
    type                varchar(255),
    status              varchar(255),
    latitude            double precision,
    longitude           double precision,
    active_years        integer,
    number_of_employees integer,
    created_at          timestamp(6) with time zone,

    constraint portfolios_type_check
        check (type in ('INDIVIDUAL', 'COMPANY')),
    constraint portfolios_status_check
        check (status in ('ACTIVE', 'INACTIVE', 'BLOCKED')),
    constraint fk_portfolios_badge
        foreign key (badge_id) references public.badges
);


-- Tables dépendant de portfolios
create table public.social_links
(
    id           uuid         not null primary key,
    portfolio_id uuid         not null,
    name         varchar(255) not null,
    platform     varchar(255),
    url          varchar(255),

    constraint social_links_url_unique
        unique (url),
    constraint social_links_portfolio_platform_unique
        unique (portfolio_id, platform),
    constraint social_links_platform_check
        check (platform in ('LINKEDIN', 'X', 'FACEBOOK', 'INSTAGRAM', 'YOUTUBE', 'GITHUB', 'DRIBBBLE', 'BEHANCE', 'WHATSAPP', 'TELEGRAM')),
    constraint fk_social_links_portfolio
        foreign key (portfolio_id) references public.portfolios
);


create table public.projects
(
    id           uuid not null primary key,
    portfolio_id uuid,
    title        varchar(500),
    description  text,
    images       text,
    start_at     date,
    completed_at date,

    constraint fk_projects_portfolio
        foreign key (portfolio_id) references public.portfolios
);


-- Tables dépendant de portfolios + categories + awards
create table public.services
(
    id           uuid         not null primary key,
    portfolio_id uuid         not null,
    category_id  uuid         not null,
    award_id     uuid,
    title        varchar(255) not null,
    description  text         not null,
    cover_image  varchar(255),
    images       text,
    pricing      text,
    status       varchar(255),

    constraint services_status_check
        check (status in ('ACTIVE', 'INACTIVE')),
    constraint fk_services_portfolio
        foreign key (portfolio_id) references public.portfolios,
    constraint fk_services_category
        foreign key (category_id) references public.categories,
    constraint fk_services_award
        foreign key (award_id) references public.awards
);


create table public.faqs
(
    id         uuid         not null primary key,
    service_id uuid         not null,
    question   varchar(255) not null,
    answer     varchar(255) not null,

    constraint fk_faqs_service
        foreign key (service_id) references public.services
);


create table public.service_tags
(
    service_id uuid not null,
    tag_id     uuid not null,

    primary key (service_id, tag_id),
    constraint fk_service_tags_service
        foreign key (service_id) references public.services,
    constraint fk_service_tags_tag
        foreign key (tag_id) references public.tags
);


create table public.likes
(
    service_id uuid not null,
    user_id    uuid not null,
    created_at timestamp(6) with time zone,

    primary key (service_id, user_id),
    constraint fk_likes_service
        foreign key (service_id) references public.services
);


create table public.reviews
(
    reviewer_id uuid    not null,
    service_id  uuid    not null,
    comment     text    not null,
    rating      integer,
    created_at  timestamp(6) with time zone,

    primary key (reviewer_id, service_id),
    constraint fk_reviews_service
        foreign key (service_id) references public.services
);


create table public.featured_services
(
    id         uuid     not null primary key,
    service_id uuid,
    priority   integer,
    source     smallint,
    created_at timestamp(6) with time zone,
    start_at   timestamp(6) with time zone,
    end_at     timestamp(6) with time zone,
    updated_at timestamp(6) with time zone,

    constraint featured_services_source_check
        check (source >= 0 and source <= 2),
    constraint fk_featured_services_service
        foreign key (service_id) references public.services
);


-- Tables dépendant de portfolios + categories
create table public.job_posts
(
    id               uuid    not null primary key,
    portfolio_id     uuid,
    category_id      uuid,
    title            varchar(255),
    description      varchar(255),
    spoken_languages varchar(255),
    job_type         varchar(255),
    work_mode        varchar(255),
    pay_period       varchar(255),
    amount_from      numeric(38, 2),
    amount_to        numeric(38, 2),
    is_negociable    boolean,
    is_closed        boolean not null,
    deadline         date,

    constraint job_posts_job_type_check
        check (job_type in ('FULL_TIME', 'PART_TIME', 'FREELANCE', 'INTERNSHIP')),
    constraint job_posts_work_mode_check
        check (work_mode in ('REMOTE', 'ON_SITE', 'HYBRID')),
    constraint job_posts_pay_period_check
        check (pay_period in ('HOURLY', 'DAILY', 'WEEKLY', 'MONTHLY'))
);


create table public.job_applications
(
    applicant_id uuid not null,
    job_post_id  uuid not null,
    motivation   text,
    status       varchar(255),
    applied_at   timestamp(6) with time zone,

    primary key (applicant_id, job_post_id),
    constraint job_applications_status_check
        check (status in ('PENDING', 'ACCEPTED', 'REJECTED')),
    constraint fk_job_applications_job_post
        foreign key (job_post_id) references public.job_posts
);


create table public.job_post_tags
(
    job_post_id uuid not null,
    tag_id      uuid not null,

    primary key (job_post_id, tag_id),
    constraint fk_job_post_tags_job_post
        foreign key (job_post_id) references public.job_posts,
    constraint fk_job_post_tags_tag
        foreign key (tag_id) references public.tags
);


create table public.featured_job_posts
(
    id          uuid     not null primary key,
    job_post_id uuid,
    priority    integer,
    source      smallint,
    created_at  timestamp(6) with time zone,
    start_at    timestamp(6) with time zone,
    end_at      timestamp(6) with time zone,
    updated_at  timestamp(6) with time zone,

    constraint featured_job_posts_source_check
        check (source >= 0 and source <= 2),
    constraint fk_featured_job_posts_job_post
        foreign key (job_post_id) references public.job_posts
);


-- Table de log sans FK (les IDs sont des références logiques non contraintes)
create table public.events_tracking
(
    id          uuid not null primary key,
    target_id   uuid,
    user_id     uuid,
    timestamp   timestamp(6) with time zone,
    event_type  varchar(255),
    target_type varchar(255),

    constraint events_tracking_event_type_check
        check (event_type in ('LIKE', 'REVIEW', 'CLICK', 'VIEW')),
    constraint events_tracking_target_type_check
        check (target_type in ('LIKE', 'REVIEW', 'PORTFOLIO', 'SERVICE', 'CONTACT_FOR_SERVICE', 'JOB_POST', 'JOB_APPLICATION'))
);
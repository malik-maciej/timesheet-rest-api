drop table if exists projects;
create table projects
(
  id          bigserial primary key,
  uuid        uuid           not null unique,
  name        varchar(255)   not null unique,
  description varchar(255),
  start_date  date           not null check (start_date < end_date),
  end_date    date           not null check (end_date > start_date),
  budget      numeric(20, 2) not null check (budget > 0)
);
create index on projects (start_date);
create index on projects (end_date);
create index on projects (budget);
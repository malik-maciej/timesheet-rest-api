drop table if exists users;
create table users
(
  id              bigserial primary key,
  uuid            uuid           not null unique,
  login           varchar(255)   not null unique,
  name            varchar(255)   not null,
  surname         varchar(255)   not null,
  type            varchar(255)   not null check (type IN ('ADMIN', 'MANAGER', 'EMPLOYEE')),
  password        varchar(255)   not null,
  email           varchar(254)   not null,
  salary_per_hour numeric(20, 2) not null check (salary_per_hour > 0)
);
create index on users (type);
create index on users (salary_per_hour);
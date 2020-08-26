drop table if exists worktimes;
create table worktimes
(
  id          bigserial primary key,
  uuid        uuid   not null unique,
  start_time  timestamp check (start_time < end_time),
  end_time    timestamp check (end_time > start_time),
  users_id    bigint not null,
  projects_id bigint not null,
  foreign key (users_id) references users (id) on update cascade,
  foreign key (projects_id) references projects (id) on update cascade
);
create index on worktimes (start_time);
create index on worktimes (end_time);
create index on worktimes (users_id);
create index on worktimes (projects_id);
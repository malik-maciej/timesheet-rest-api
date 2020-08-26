drop table if exists users_projects;
create table users_projects
(
  users_id    bigint not null,
  projects_id bigint not null,
  primary key (users_id, projects_id),
  foreign key (users_id) references users (id) on delete cascade on update cascade,
  foreign key (projects_id) references projects (id) on update cascade
);
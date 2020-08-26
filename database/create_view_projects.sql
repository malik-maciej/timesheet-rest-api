CREATE OR REPLACE VIEW projects_view AS
SELECT p.id,
       p.uuid,
       p.name,
       p.description,
       p.start_date,
       p.end_date,
       p.budget,
       (((select extract(epoch from wt.end_time - wt.start_time) / 3600)
         * u.salary_per_hour * 100) / p.budget) as budget_percentage,
       ((select extract(epoch from wt.end_time - wt.start_time) / 3600)
         * u.salary_per_hour) > p.budget       as budget_exceeded,
       u                                       as users
FROM projects p
       LEFT JOIN worktimes wt on p.id = wt.projects_id
       LEFT JOIN users u on wt.users_id = u.id
       LEFT JOIN users_projects up on p.id = up.projects_id
GROUP BY p.id, p.uuid, p.name, p.description, p.start_date, p.end_date, p.budget,
         budget_percentage, budget_exceeded, users;
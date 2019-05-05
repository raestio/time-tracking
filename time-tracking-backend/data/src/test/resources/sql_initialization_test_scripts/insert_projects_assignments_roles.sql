INSERT INTO time_tracking_schema.work_type(id, "name") VALUES (-1, 'test work type');
INSERT INTO time_tracking_schema.project(id, "name", "start") VALUES (-1, 'test project', CURRENT_DATE);
INSERT INTO time_tracking_schema.user(id, "name", surname, email, auth_provider) VALUES (-1, 'test', 'testovic', 'tmp@ahoj.cau', 'GOOGLE');

INSERT INTO time_tracking_schema.project_assignment(id, valid_from, id_project, id_user) VALUES (-1, '2019-04-24', -1, -1);
INSERT INTO time_tracking_schema.project_role(id, "name") VALUES (-1, 'DEVELOPER');
INSERT INTO time_tracking_schema.project_roles_assignment(id, id_project_role, id_project_assignment) VALUES (-1, -1, -1);

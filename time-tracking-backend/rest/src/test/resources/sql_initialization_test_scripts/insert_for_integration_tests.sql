INSERT INTO time_tracking_schema.user_role(id, "name", description) VALUES
(-1, 'USER', NULL),
(-2, 'ADMIN', NULL);

INSERT INTO time_tracking_schema.project_role(id, "name", description) VALUES
(-1, 'MEMBER', NULL),
(-2, 'PROJECT_MANAGER', NULL);


INSERT INTO time_tracking_schema.work_type(id, "name") VALUES (-1, 'vyvoj');
INSERT INTO time_tracking_schema.work_type(id, "name") VALUES (-2, 'analyza');

INSERT INTO time_tracking_schema.project(id, "name", "start") VALUES (-1, 'test project', '2019-04-04');
INSERT INTO time_tracking_schema.project_work_type(id, id_work_type, id_project) VALUES (-1, -1, -1);
INSERT INTO time_tracking_schema.project_work_type(id, id_work_type, id_project) VALUES (-2, -2, -1);

INSERT INTO time_tracking_schema.project(id, "name", "start") VALUES (-2, 'test project 2', '2019-04-10');
INSERT INTO time_tracking_schema.project_work_type(id, id_work_type, id_project) VALUES (-3, -1, -2);

INSERT INTO time_tracking_schema.user(id, "name", surname, email, auth_provider) VALUES (-1, 'admin', 'testovic', 'admin@ahoj.cau', 'GOOGLE');
INSERT INTO time_tracking_schema.user_roles_assignment(id, id_user, id_user_role) VALUES (-1, -1, -1);
INSERT INTO time_tracking_schema.user_roles_assignment(id, id_user, id_user_role) VALUES (-2, -1, -2);

INSERT INTO time_tracking_schema.user(id, "name", surname, email, auth_provider) VALUES (-2, 'user', 'test', 'user@ahoj2.cau', 'GOOGLE');
INSERT INTO time_tracking_schema.user_roles_assignment(id, id_user, id_user_role) VALUES (-3, -2, -1);


INSERT INTO time_tracking_schema.user(id, "name", surname, email, auth_provider) VALUES (-3, 'user but project manager', 'test', 'pmanager@ahoj2.cau', 'GOOGLE');
INSERT INTO time_tracking_schema.user_roles_assignment(id, id_user, id_user_role) VALUES (-4, -3, -1);
INSERT INTO time_tracking_schema.project_assignment(id, valid_from, id_project, id_user) VALUES (-1, '2019-04-04', -1, -3);
INSERT INTO time_tracking_schema.project_roles_assignment(id, id_project_role, id_project_assignment) VALUES (-1, -1, -1);
INSERT INTO time_tracking_schema.project_roles_assignment(id, id_project_role, id_project_assignment) VALUES (-2, -2, -1);


INSERT INTO time_tracking_schema.user(id, "name", surname, email, auth_provider) VALUES (-4, 'user and project member', 'test', 'project_member@ahoj2.cau', 'GOOGLE');
INSERT INTO time_tracking_schema.user_roles_assignment(id, id_user, id_user_role) VALUES (-5, -4, -1);
INSERT INTO time_tracking_schema.project_assignment(id, valid_from, id_project, id_user) VALUES (-2, '2019-04-10', -1, -4);
INSERT INTO time_tracking_schema.project_roles_assignment(id, id_project_role, id_project_assignment) VALUES (-3, -1, -2);

INSERT INTO time_tracking_schema.project_assignment(id, valid_from, id_project, id_user) VALUES (-3, '2019-04-15', -2, -4);
INSERT INTO time_tracking_schema.project_roles_assignment(id, id_project_role, id_project_assignment) VALUES (-4, -1, -3);





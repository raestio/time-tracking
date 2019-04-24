INSERT INTO time_tracking_schema.work_type(id, "name") VALUES (-1, 'test work type');
INSERT INTO time_tracking_schema.project(id, "name", "start") VALUES (-1, 'test project', CURRENT_DATE);
INSERT INTO time_tracking_schema.user(id, "name", surname, email, auth_provider) VALUES (-1, 'test', 'testovic', 'tmp@ahoj.cau', 'GOOGLE');

INSERT INTO time_tracking_schema.work_record(id, date_from, date_to, description, date_created, id_project, id_work_type, id_user)
VALUES (-1, '2019-04-24 05:00:00', '2019-04-24 06:00:00', 'jira tasks', CURRENT_DATE, -1, -1, -1);
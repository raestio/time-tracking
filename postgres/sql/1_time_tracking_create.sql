DROP SCHEMA IF EXISTS time_tracking_schema CASCADE;
CREATE SCHEMA time_tracking_schema;

CREATE TABLE time_tracking_schema.user_role (
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) UNIQUE NOT NULL,
	description VARCHAR(255) NULL
);

CREATE TABLE time_tracking_schema."user" (
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	surname VARCHAR(50) NOT NULL,
	email VARCHAR(50) UNIQUE NOT NULL,
	picture_url VARCHAR(600) NULL,
	auth_provider VARCHAR(50) NOT NULL
);

CREATE INDEX idx_user_email
ON time_tracking_schema."user"(email);

CREATE TABLE time_tracking_schema.user_roles_assignment (
	id SERIAL PRIMARY KEY,
	id_user INTEGER REFERENCES time_tracking_schema."user"(id) NOT NULL,
	id_user_role INTEGER REFERENCES time_tracking_schema.user_role(id) NOT NULL,
	UNIQUE (id_user, id_user_role)
);

CREATE TABLE time_tracking_schema.project_role (
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) UNIQUE NOT NULL,
	description VARCHAR(255) NULL
);

CREATE TABLE time_tracking_schema.project (
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) NOT NULL,
	description VARCHAR(255) NULL,
	"start" date NOT NULL,
	"end" date NULL
);

CREATE TABLE time_tracking_schema.work_type (
	id SERIAL PRIMARY KEY,
	name VARCHAR(50) UNIQUE NOT NULL,
	description VARCHAR(255) NULL
);

CREATE TABLE time_tracking_schema.project_work_type (
	id SERIAL PRIMARY KEY,
	id_work_type INTEGER REFERENCES time_tracking_schema.work_type(id) NOT NULL,
	id_project INTEGER REFERENCES time_tracking_schema.project(id) NOT NULL,
	UNIQUE (id_project, id_work_type)
);

CREATE INDEX idx_project_work_type_project
ON time_tracking_schema.project_work_type(id_project);

CREATE INDEX idx_project_work_type_work_type
ON time_tracking_schema.project_work_type(id_work_type);

CREATE TABLE time_tracking_schema.project_assignment (
	id SERIAL PRIMARY KEY,
	valid_from date NOT NULL,
	valid_to date NULL,
	id_user INTEGER REFERENCES time_tracking_schema."user"(id) NOT NULL,
	id_project INTEGER REFERENCES time_tracking_schema.project(id) NOT NULL
);

CREATE TABLE time_tracking_schema.project_roles_assignment (
	id SERIAL PRIMARY KEY,
	id_project_role INTEGER REFERENCES time_tracking_schema.project_role(id) NOT NULL,
	id_project_assignment INTEGER REFERENCES time_tracking_schema.project_assignment(id) NOT NULL,
	UNIQUE (id_project_role, id_project_assignment)
);

CREATE TABLE time_tracking_schema.work_record (
	id SERIAL PRIMARY KEY,
	date_from TIMESTAMP NOT NULL,
	date_to TIMESTAMP NOT NULL CHECK (date_to > date_from),
	description VARCHAR(255) NOT NULL,
	date_created TIMESTAMP NOT NULL,
	date_updated TIMESTAMP NULL,
	id_project INTEGER REFERENCES time_tracking_schema.project(id) NOT NULL,
	id_work_type INTEGER REFERENCES time_tracking_schema.work_type(id) NOT NULL,
	id_user INTEGER REFERENCES time_tracking_schema."user"(id) NOT NULL
);

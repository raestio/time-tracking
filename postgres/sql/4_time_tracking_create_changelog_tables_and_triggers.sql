DROP SCHEMA IF EXISTS time_tracking_changelog_schema CASCADE;
CREATE SCHEMA time_tracking_changelog_schema;

CREATE TABLE time_tracking_changelog_schema.changelog (
	id SERIAL PRIMARY KEY,
	id_row_changed INTEGER NOT NULL,
	"table" VARCHAR(50) NOT NULL,
	action VARCHAR(50) NOT NULL,
	stamp timestamp
);

CREATE OR REPLACE FUNCTION process_changelog() RETURNS TRIGGER AS $process_changelog$
    BEGIN
        IF (TG_OP = 'DELETE') THEN
            INSERT INTO time_tracking_changelog_schema.changelog(id_row_changed, "table", "action", stamp) VALUES (OLD.id, TG_ARGV[0], 'delete', now());
            RETURN OLD;
        ELSIF (TG_OP = 'UPDATE') THEN
            INSERT INTO time_tracking_changelog_schema.changelog(id_row_changed, "table", "action", stamp) VALUES (OLD.id, TG_ARGV[0], 'update', now());
            RETURN NEW;
        ELSIF (TG_OP = 'INSERT') THEN
            INSERT INTO time_tracking_changelog_schema.changelog(id_row_changed, "table", "action", stamp) VALUES (NEW.id, TG_ARGV[0], 'create', now());
            RETURN NEW;
        END IF;
        RETURN NULL;
    END;
$process_changelog$ LANGUAGE plpgsql;

CREATE TRIGGER users_changelog_trigger
AFTER INSERT OR UPDATE OR DELETE ON time_tracking_schema.user
    FOR EACH ROW EXECUTE PROCEDURE process_changelog('user');

CREATE TRIGGER work_records_changelog_trigger
AFTER INSERT OR UPDATE OR DELETE ON time_tracking_schema.work_record
    FOR EACH ROW EXECUTE PROCEDURE process_changelog('work_record');

CREATE TRIGGER projects_changelog_trigger
AFTER INSERT OR UPDATE OR DELETE ON time_tracking_schema.project
    FOR EACH ROW EXECUTE PROCEDURE process_changelog('project');


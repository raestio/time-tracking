package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.configuration.DataTestsConfiguration;
import cz.cvut.fit.timetracking.data.entity.Project;
import cz.cvut.fit.timetracking.data.repository.ProjectRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "/sql_initialization_test_scripts/insert_projects_assignments_roles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql_initialization_test_scripts/delete_projects_assignments_roles.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProjectTests extends DataTestsConfiguration {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    public void testProjectAssignemntsWithProjectRolesFetch() {
        Project project = projectRepository.findWithAssignmentsAndProjectRolesFetchedById(-1).get();
        project.getProjectAssignments().forEach(a -> assertThat(a.getValidFrom()).isEqualTo("2019-04-24"));
        assertThat(project.getProjectAssignments().size()).isEqualTo(1);
        project.getProjectAssignments().forEach(a -> a.getProjectRoles().forEach(r -> assertThat(r.getName().toString()).isEqualTo("DEVELOPER")));
    }

}

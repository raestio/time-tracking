package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.configuration.DataTestsConfiguration;
import cz.cvut.fit.timetracking.data.api.dto.AuthProvider;
import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleName;
import cz.cvut.fit.timetracking.data.entity.Project;
import cz.cvut.fit.timetracking.data.repository.ProjectRepository;
import cz.cvut.fit.timetracking.data.service.ProjectDataService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "/sql_initialization_test_scripts/insert_projects_assignments_roles.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql_initialization_test_scripts/delete_projects_assignments_roles.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ProjectTests extends DataTestsConfiguration {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectDataService projectDataService;

    @Test
    public void testProjectAssignmentsWithProjectRolesFetch() {
        Project project = projectRepository.findWithAssignmentsAndProjectRolesFetchedById(-1).get();
        project.getProjectAssignments().forEach(a -> assertThat(a.getValidFrom()).isEqualTo("2019-04-24"));
        assertThat(project.getProjectAssignments().size()).isEqualTo(1);
        project.getProjectAssignments().forEach(a -> a.getProjectRoles().forEach(r -> assertThat(r.getName().toString()).isEqualTo("DEVELOPER")));
    }

    @Test
    public void testFindProjectWithProjectAssignmentsWithProjectRolesById() {
        List<ProjectAssignmentDTO> projectAssignmentDTOList = projectDataService.findProjectAssignmentsByProjectId(-1);
        assertThat(projectAssignmentDTOList.size()).isEqualTo(1);
        assertThat(projectAssignmentDTOList.get(0).getId()).isEqualTo(-1);
        assertThat(projectAssignmentDTOList.get(0).getUser().getEmail()).isEqualTo("tmp@ahoj.cau");
        assertThat(projectAssignmentDTOList.get(0).getUser().getAuthProvider()).isEqualTo(AuthProvider.GOOGLE);
        assertThat(projectAssignmentDTOList.get(0).getValidFrom()).isEqualTo("2019-04-24");
        projectAssignmentDTOList.get(0).getProjectRoles().forEach(role -> assertThat(role.getName()).isEqualTo(ProjectRoleName.DEVELOPER));
    }
}

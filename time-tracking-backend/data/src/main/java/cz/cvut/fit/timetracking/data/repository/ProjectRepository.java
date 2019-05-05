package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @EntityGraph(value = "Project.projectAssignmentsWithProjectRoles", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT p FROM Project p WHERE p.id = :projectId")
    Optional<Project> findWithAssignmentsAndProjectRolesFetchedById(@Param("projectId") Integer projectId);
}

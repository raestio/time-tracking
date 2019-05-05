package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @EntityGraph(value = "Project.workTypes", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT p FROM Project p WHERE p.id = :projectId")
    Optional<Project> findWithWorkTypesFetchedById(@Param("projectId") Integer projectId);

    @EntityGraph(value = "Project.workTypes", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT p FROM Project p")
    List<Project> findAllWithWorkTypesFetched();

    @EntityGraph(value = "Project.projectAssignmentsWithProjectRoles", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT p FROM Project p WHERE p.id = :projectId")
    Optional<Project> findWithAssignmentsAndProjectRolesFetchedById(@Param("projectId") Integer projectId);

    @EntityGraph(value = "Project.workTypes", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT p FROM Project p JOIN p.projectAssignments a " +
            "WHERE a.validFrom <= :date AND " +
            "(a.validTo IS NULL OR :date <= a.validTo) AND " +
            "a.user.id = :userId")
    List<Project> findAllAssignedProjectsWhereValidTimeOverlapsByUserId(@Param("date") LocalDate date, @Param("userId") Integer userId);
}

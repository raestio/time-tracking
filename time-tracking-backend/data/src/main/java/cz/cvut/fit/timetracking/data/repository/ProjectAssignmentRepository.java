package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.ProjectAssignment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Integer> {

    @EntityGraph(value = "ProjectAssignment.projectRoles", type = EntityGraph.EntityGraphType.LOAD)
    Optional<ProjectAssignment> findById(@Param("id") Integer id);

    @EntityGraph(value = "ProjectAssignment.projectRoles", type = EntityGraph.EntityGraphType.LOAD)
    List<ProjectAssignment> findAllByProjectIdAndUserId(Integer projectId, Integer userId);
}

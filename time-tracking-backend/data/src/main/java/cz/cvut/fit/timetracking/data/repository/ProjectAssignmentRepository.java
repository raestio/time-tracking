package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.ProjectAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Integer> {
}

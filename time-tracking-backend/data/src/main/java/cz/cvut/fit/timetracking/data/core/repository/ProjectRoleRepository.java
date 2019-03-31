package cz.cvut.fit.timetracking.data.core.repository;

import cz.cvut.fit.timetracking.data.core.entity.Project;
import cz.cvut.fit.timetracking.data.core.entity.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Integer> {
}

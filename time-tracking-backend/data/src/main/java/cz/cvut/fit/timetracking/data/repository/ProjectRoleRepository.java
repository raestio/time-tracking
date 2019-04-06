package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.ProjectRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Integer> {
}

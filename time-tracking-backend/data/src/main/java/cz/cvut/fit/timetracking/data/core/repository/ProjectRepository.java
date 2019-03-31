package cz.cvut.fit.timetracking.data.core.repository;

import cz.cvut.fit.timetracking.data.core.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}

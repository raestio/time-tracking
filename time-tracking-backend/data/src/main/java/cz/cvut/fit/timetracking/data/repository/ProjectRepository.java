package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}

package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.ProjectRole;
import cz.cvut.fit.timetracking.data.entity.enums.ProjectRoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRoleRepository extends JpaRepository<ProjectRole, Integer> {
    Optional<ProjectRole> findByName(ProjectRoleName projectRoleName);
    List<ProjectRole> findByNameIn(List<ProjectRoleName> projectRoleNames);
}

package cz.cvut.fit.timetracking.data.core.repository;

import cz.cvut.fit.timetracking.data.core.entity.Project;
import cz.cvut.fit.timetracking.data.core.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
}

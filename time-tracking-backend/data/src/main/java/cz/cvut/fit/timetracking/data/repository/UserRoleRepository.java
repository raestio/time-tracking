package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.UserRole;
import cz.cvut.fit.timetracking.data.entity.enums.UserRoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {
    @Query("SELECT r FROM UserRole r LEFT OUTER JOIN r.users u WHERE u.id = :id")
    List<UserRole> findAllByUserId(@Param("id") Integer id);

    List<UserRole> findAllByNameIn(List<UserRoleName> names);
}

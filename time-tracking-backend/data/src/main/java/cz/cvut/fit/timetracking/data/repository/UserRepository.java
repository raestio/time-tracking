package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(value = "User.userRoles", type = EntityGraph.EntityGraphType.LOAD)
    Optional<User> findById(@Param("id") Integer id);

    @EntityGraph(value = "User.userRoles", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
}

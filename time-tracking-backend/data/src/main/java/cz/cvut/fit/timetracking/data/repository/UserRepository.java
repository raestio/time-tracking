package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}

package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

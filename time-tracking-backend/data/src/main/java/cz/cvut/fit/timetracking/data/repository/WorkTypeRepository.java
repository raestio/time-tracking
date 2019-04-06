package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTypeRepository extends JpaRepository<WorkType, Integer> {
}

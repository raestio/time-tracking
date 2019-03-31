package cz.cvut.fit.timetracking.data.core.repository;

import cz.cvut.fit.timetracking.data.core.entity.WorkType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkTypeRepository extends JpaRepository<WorkType, Integer> {
}

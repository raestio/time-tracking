package cz.cvut.fit.timetracking.data.core.repository;

import cz.cvut.fit.timetracking.data.core.entity.Project;
import cz.cvut.fit.timetracking.data.core.entity.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Integer> {
}

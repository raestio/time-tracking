package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Integer> {
}

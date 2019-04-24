package cz.cvut.fit.timetracking.data.repository;

import cz.cvut.fit.timetracking.data.entity.WorkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkRecordRepository extends JpaRepository<WorkRecord, Integer> {

    @Query("SELECT w FROM WorkRecord w WHERE w.user.id = :userId AND NOT (:dateFrom >= w.dateTo OR :dateTo <= w.dateFrom)")
    List<WorkRecord> findRecordsThatOverlap(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo, @Param("userId") Integer userId);
}

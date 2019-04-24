package cz.cvut.fit.timetracking.workrecord.service.impl;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTO;
import cz.cvut.fit.timetracking.user.service.UserService;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.exception.WorkRecordServiceException;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WorkRecordServiceImpl implements WorkRecordService {

    @Autowired
    private DataAccessApi dataAccessApi;

    @Autowired
    private UserService userService;

    @Override
    public WorkRecord create(LocalDateTime from, LocalDateTime to, String description, Integer projectId, Integer workTypeId, Integer userId) {
        checkWorkRecordsOverlapOrThrow(from, to, userId);
        WorkRecordDTO workRecordDTO = new WorkRecordDTO();
        workRecordDTO.setDateFrom(from);
        workRecordDTO.setDateTo(to);
        workRecordDTO.setDescription(description);
        workRecordDTO.setDateCreated(LocalDateTime.now());
        fillUser(workRecordDTO, userId);
        fillProject(workRecordDTO, projectId);
        fillWorkType();
        workRecordDTO.setUser();
        return ;
    }

    @Override
    public Optional<WorkRecord> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Integer id) {

    }

    private void checkWorkRecordsOverlapOrThrow(LocalDateTime from, LocalDateTime to, Integer userId) {
        Assert.isTrue(to.isAfter(from), "Work record time parameter 'to' must go after time parameter 'from'.");
        boolean overlap = dataAccessApi.workRecordTimesOverlapWithOtherUserRecords(from, to, userId);
        if (overlap) {
            throw new WorkRecordServiceException("Work record times cannot overlap.");
        }
    }
}

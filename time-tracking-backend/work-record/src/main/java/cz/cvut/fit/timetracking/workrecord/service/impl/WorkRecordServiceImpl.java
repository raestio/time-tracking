package cz.cvut.fit.timetracking.workrecord.service.impl;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTO;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTOLight;
import cz.cvut.fit.timetracking.project.exception.ProjectNotFoundException;
import cz.cvut.fit.timetracking.project.service.ProjectService;
import cz.cvut.fit.timetracking.user.service.UserService;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.exception.WorkRecordNotFoundException;
import cz.cvut.fit.timetracking.workrecord.exception.WorkRecordServiceException;
import cz.cvut.fit.timetracking.workrecord.mapper.WorkRecordModelMapper;
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

    @Autowired
    private WorkRecordModelMapper workRecordModelMapper;

    @Autowired
    private ProjectService projectService;

    @Override
    public WorkRecord create(LocalDateTime from, LocalDateTime to, String description, int projectId, int workTypeId, int userId) {
        checkWorkRecordConstraintsOrThrow(from, to, userId, projectId, workTypeId);
        WorkRecordDTOLight workRecordDTOLight = new WorkRecordDTOLight();
        workRecordDTOLight.setDateFrom(from);
        workRecordDTOLight.setDateTo(to);
        workRecordDTOLight.setDescription(description);
        workRecordDTOLight.setDateCreated(LocalDateTime.now());
        workRecordDTOLight.setUserId(userId);
        workRecordDTOLight.setProjectId(projectId);
        workRecordDTOLight.setWorkTypeId(workTypeId);
        WorkRecordDTOLight workRecordDTOLightCreated = dataAccessApi.createOrUpdateWorkRecord(workRecordDTOLight);
        WorkRecordDTO workRecordDTO = dataAccessApi.findWorkRecordById(workRecordDTOLightCreated.getId()).get();
        return map(workRecordDTO);
    }

    @Override
    public WorkRecord update(int workRecordId, LocalDateTime from, LocalDateTime to, String description, int projectId, int workTypeId) {
        WorkRecordDTO workRecord = dataAccessApi.findWorkRecordById(workRecordId).orElseThrow(() -> new WorkRecordNotFoundException(workRecordId));
        checkWorkRecordConstraintsOrThrow(from, to, workRecord.getUser().getId(), projectId, workTypeId);
        workRecord.setDateFrom(from);
        workRecord.setDateTo(to);
        workRecord.setDescription(description);
        workRecord.getProject().setId(projectId);
        workRecord.getWorkType().setId(workTypeId);
        workRecord.setDateUpdated(LocalDateTime.now());
        WorkRecordDTO updatedWorkRecord = dataAccessApi.createOrUpdateWorkRecord(workRecord);
        return map(updatedWorkRecord);
    }

    @Override
    public Optional<WorkRecord> findById(int id) {
        Optional<WorkRecord> result = dataAccessApi.findWorkRecordById(id).map(this::map);
        return result;
    }

    @Override
    public void deleteById(int id) {
        findById(id).orElseThrow(() -> new WorkRecordNotFoundException(id));
        dataAccessApi.deleteWorkRecordById(id);
    }

    private void checkWorkRecordConstraintsOrThrow(LocalDateTime from, LocalDateTime to, Integer userId, int projectId, int workTypeId) {
        Assert.isTrue(to.isAfter(from), "Work record time parameter 'to' must go after time parameter 'from'.");
        boolean overlap = dataAccessApi.workRecordTimesOverlapWithOtherUserRecords(from, to, userId);
        if (overlap) {
            throw new WorkRecordServiceException("Work record times cannot overlap.");
        }
        projectService.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
    }

    private WorkRecord map(WorkRecordDTO workRecordDTO) {
        var result = workRecordModelMapper.map(workRecordDTO, WorkRecord.class);
        return result;
    }
}

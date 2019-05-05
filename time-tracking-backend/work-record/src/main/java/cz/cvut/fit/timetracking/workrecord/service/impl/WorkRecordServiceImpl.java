package cz.cvut.fit.timetracking.workrecord.service.impl;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTO;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTOLight;
import cz.cvut.fit.timetracking.jira.service.JiraUserService;
import cz.cvut.fit.timetracking.project.exception.ProjectNotFoundException;
import cz.cvut.fit.timetracking.project.service.ProjectService;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.exception.WorkRecordNotFoundException;
import cz.cvut.fit.timetracking.workrecord.exception.WorkRecordServiceException;
import cz.cvut.fit.timetracking.workrecord.mapper.WorkRecordModelMapper;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WorkRecordServiceImpl implements WorkRecordService {

    @Autowired
    private DataAccessApi dataAccessApi;

    @Autowired
    private WorkRecordModelMapper workRecordModelMapper;

    @Autowired
    private ProjectService projectService;

    @Override
    public WorkRecord create(LocalDateTime from, LocalDateTime to, String description, Integer projectId, Integer workTypeId, Integer userId) {
        checkWorkRecordConstraintsOrThrow(from, to, userId, projectId);
        WorkRecordDTOLight workRecordDTOLight = new WorkRecordDTOLight();
        workRecordDTOLight.setDateFrom(from);
        workRecordDTOLight.setDateTo(to);
        workRecordDTOLight.setDescription(description);
        workRecordDTOLight.setDateCreated(LocalDateTime.now());
        workRecordDTOLight.setUserId(userId);
        workRecordDTOLight.setProjectId(projectId);
        workRecordDTOLight.setWorkTypeId(workTypeId);
        WorkRecordDTOLight workRecordDTOLightCreated = dataAccessApi.createOrUpdateWorkRecord(workRecordDTOLight);
        WorkRecordDTO workRecordDTO = dataAccessApi.findWorkRecordById(workRecordDTOLightCreated.getId()).get(); //must be not null
        return map(workRecordDTO);
    }

    @Override
    public WorkRecord update(Integer workRecordId, LocalDateTime from, LocalDateTime to, String description, Integer projectId, Integer workTypeId) {
        WorkRecordDTO workRecord = dataAccessApi.findWorkRecordById(workRecordId).orElseThrow(() -> new WorkRecordNotFoundException(workRecordId));
        checkWorkRecordConstraintsOrThrow(from, to, workRecord.getUser().getId(), projectId);
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
    public Optional<WorkRecord> findById(Integer id) {
        Optional<WorkRecord> result = dataAccessApi.findWorkRecordById(id).map(this::map);
        return result;
    }

    @Override
    public List<WorkRecord> findAllBetween(LocalDateTime fromInclusive, LocalDateTime toExclusive) {
        Assert.notNull(fromInclusive, "fromInclusive cannot be null");
        Assert.notNull(toExclusive, "toExclusive cannot be null");
        List<WorkRecordDTO> workRecordDTOs = dataAccessApi.findAllBetween(fromInclusive, toExclusive);
        List<WorkRecord> workRecords = workRecordDTOs.stream().map(this::map).collect(Collectors.toList());
        return workRecords;
    }

    @Override
    public List<WorkRecord> findAllBetweenByUserId(LocalDateTime fromInclusive, LocalDateTime toExclusive, Integer userId) {
        Assert.notNull(userId, "userId cannot be null");
        Assert.notNull(fromInclusive, "fromInclusive cannot be null");
        Assert.notNull(toExclusive, "toExclusive cannot be null");
        List<WorkRecordDTO> workRecordDTOs = dataAccessApi.findAllBetweenByUserId(fromInclusive, toExclusive, userId);
        List<WorkRecord> workRecords = workRecordDTOs.stream().map(this::map).collect(Collectors.toList());
        return workRecords;
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).orElseThrow(() -> new WorkRecordNotFoundException(id));
        dataAccessApi.deleteWorkRecordById(id);
    }

    private void checkWorkRecordConstraintsOrThrow(LocalDateTime from, LocalDateTime to, Integer userId, Integer projectId) {
        Assert.notNull(userId, "user id cannot be null");
        Assert.notNull(projectId, "project id cannot be null");
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

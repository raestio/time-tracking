package cz.cvut.fit.timetracking.workrecord.service.impl;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTO;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTOLight;
import cz.cvut.fit.timetracking.project.dto.Project;
import cz.cvut.fit.timetracking.project.exception.ProjectNotFoundException;
import cz.cvut.fit.timetracking.project.exception.WorkTypeNotFoundException;
import cz.cvut.fit.timetracking.project.service.ProjectService;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.dto.input.WorkRecordInput;
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
        WorkRecordDTO workRecordDTO = dataAccessApi.findWorkRecordById(workRecordDTOLightCreated.getId()).get(); //must be not null
        return map(workRecordDTO);
    }

    @Override
    public void create(List<WorkRecordInput> workRecordInputs) {
        workRecordInputs.forEach(input -> checkWorkRecordConstraintsOrThrow(input.getDateFrom(), input.getDateTo(), input.getUserId(), input.getProjectId(), input.getWorkTypeId()));
        workRecordInputs.forEach(input -> create(input.getDateFrom(), input.getDateTo(), input.getDescription(), input.getProjectId(), input.getWorkTypeId(), input.getUserId()));
    }

    @Override
    public WorkRecord update(Integer workRecordId, LocalDateTime from, LocalDateTime to, String description, Integer projectId, Integer workTypeId) {
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
    public Optional<WorkRecord> findById(Integer id) {
        Optional<WorkRecord> result = dataAccessApi.findWorkRecordById(id).map(this::map);
        return result;
    }

    @Override
    public List<WorkRecord> findAllBetween(LocalDateTime fromInclusive, LocalDateTime toExclusive) {
        Assert.notNull(fromInclusive, "fromInclusive cannot be null");
        Assert.notNull(toExclusive, "toExclusive cannot be null");
        List<WorkRecordDTO> workRecordDTOs = dataAccessApi.findAllWorkRecordsBetween(fromInclusive, toExclusive);
        List<WorkRecord> workRecords = workRecordDTOs.stream().map(this::map).collect(Collectors.toList());
        return workRecords;
    }

    @Override
    public List<WorkRecord> findAllBetweenByUserId(LocalDateTime fromInclusive, LocalDateTime toExclusive, Integer userId) {
        Assert.notNull(userId, "userId cannot be null");
        Assert.notNull(fromInclusive, "fromInclusive cannot be null");
        Assert.notNull(toExclusive, "toExclusive cannot be null");
        List<WorkRecordDTO> workRecordDTOs = dataAccessApi.findAllWorkRecordsBetweenByUserId(fromInclusive, toExclusive, userId);
        List<WorkRecord> workRecords = workRecordDTOs.stream().map(this::map).collect(Collectors.toList());
        return workRecords;
    }

    @Override
    public void deleteById(Integer id) {
        findById(id).orElseThrow(() -> new WorkRecordNotFoundException(id));
        dataAccessApi.deleteWorkRecordById(id);
    }

    private void checkWorkRecordConstraintsOrThrow(LocalDateTime from, LocalDateTime to, Integer userId, Integer projectId, Integer workTypeId) {
        Assert.notNull(userId, "user id cannot be null");
        Assert.notNull(projectId, "project id cannot be null");
        Assert.isTrue(to.isAfter(from), "Work record time parameter 'to' must go after time parameter 'from'.");

        boolean overlap = workRecordTimesOverlapWithOtherUserRecords(from, to, userId);
        if (overlap) {
            throw new WorkRecordServiceException("Work record times cannot overlap. projectId: " + projectId + " userId: " + userId + " from: " + from.toString() + " to: " + to.toString());
        }

        boolean isAssigned = projectService.isUserAssignedToProject(userId, projectId);
        if (!isAssigned) {
            throw new WorkRecordServiceException("Work record cannot be created. User with id = " + userId + " has no valid project assignment with project id = " + projectId);
        }

        Project project = projectService.findById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        boolean workTypeExists = project.getWorkTypes().stream().anyMatch(workType -> workType.getId().equals(workTypeId));
        if (!workTypeExists) {
            throw new WorkTypeNotFoundException("Work type with id: " + workTypeId + " doesn't exist in project with id: " + projectId);
        }
    }

    @Override
    public boolean workRecordTimesOverlapWithOtherUserRecords(LocalDateTime from, LocalDateTime to, Integer userId) {
        return dataAccessApi.workRecordTimesOverlapWithOtherUserRecords(from, to, userId);
    }

    private WorkRecord map(WorkRecordDTO workRecordDTO) {
        var result = workRecordModelMapper.map(workRecordDTO, WorkRecord.class);
        return result;
    }
}

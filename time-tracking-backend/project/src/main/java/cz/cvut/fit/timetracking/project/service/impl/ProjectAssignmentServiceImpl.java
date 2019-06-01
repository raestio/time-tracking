package cz.cvut.fit.timetracking.project.service.impl;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTOLight;
import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleDTO;
import cz.cvut.fit.timetracking.project.dto.ProjectAssignment;
import cz.cvut.fit.timetracking.project.dto.ProjectRoleName;
import cz.cvut.fit.timetracking.project.exception.ProjectAssignmentException;
import cz.cvut.fit.timetracking.project.exception.ProjectAssignmentNotFoundException;
import cz.cvut.fit.timetracking.project.mapper.ProjectModelMapper;
import cz.cvut.fit.timetracking.project.service.ProjectAssignmentService;
import cz.cvut.fit.timetracking.user.exception.UserNotFoundException;
import cz.cvut.fit.timetracking.user.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ProjectAssignmentServiceImpl implements ProjectAssignmentService {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private DataAccessApi dataAccessApi;

    @Autowired
    private ProjectModelMapper projectModelMapper;

    @Autowired
    private UserService userService;

    @Override
    public List<ProjectAssignment> findByProjectId(Integer projectId) {
        Assert.notNull(projectId, "project id cannot be null");
        List<ProjectAssignmentDTO> projectAssignmentDTOs = dataAccessApi.findProjectAssignmentsByProjectId(projectId);
        List<ProjectAssignment> projectAssignments = projectAssignmentDTOs.stream().map(this::map).collect(Collectors.toList());
        return projectAssignments;
    }

    @Override
    public Optional<ProjectAssignment> findById(Integer id) {
        Assert.notNull(id, "project assignment id cannot be null");
        Optional<ProjectAssignmentDTO> projectAssignmentDTO = dataAccessApi.findProjectAssignmentById(id);
        return projectAssignmentDTO.map(this::map);
    }

    @Override
    public ProjectAssignment create(Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo) {
        return create(projectId, userId, validFrom, validTo, List.of(ProjectRoleName.MEMBER));
    }

    @Override
    public ProjectAssignment create(Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo, List<ProjectRoleName> projectRoleNames) {
        if (!memberRoleExists(projectRoleNames)) {
            LOGGER.info("Project assignment creation - missing MEMBER project role... MEMBER project role added as default");
            projectRoleNames.add(ProjectRoleName.MEMBER);
        }
        return createOrUpdate(null, projectId, userId, validFrom, validTo, projectRoleNames);
    }

    @Override
    public ProjectAssignment update(Integer projectAssignmentId, Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo, List<ProjectRoleName> projectRoleNames) {
        findById(projectAssignmentId).orElseThrow(() -> new ProjectAssignmentNotFoundException(projectAssignmentId));
        return createOrUpdate(projectAssignmentId, projectId, userId, validFrom, validTo, projectRoleNames);
    }

    @Override
    public void deleteProjectAssignmentById(Integer id) {
        Assert.notNull(id, "id cannot be null");
        Optional<ProjectAssignmentDTO> projectAssignmentDTO = dataAccessApi.findProjectAssignmentById(id);
        projectAssignmentDTO.ifPresentOrElse(p -> dataAccessApi.deleteProjectAssignmentById(id), () -> { throw new ProjectAssignmentNotFoundException(id); });
    }

    private ProjectAssignment createOrUpdate(Integer projectAssignmentId, Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo, List<ProjectRoleName> projectRoleNames) {
        checkConstraintsForCreateOrUpdate(projectId, userId, validFrom, validTo, projectRoleNames, projectAssignmentId);
        List<ProjectRoleDTO> projectRoleDTOs = dataAccessApi.findAllProjectRolesIn(projectRoleNames.stream().map(this::map).collect(Collectors.toList()));
        Assert.isTrue(projectRoleDTOs.size() == projectRoleNames.size(), "All project roles must be persisted in DB");
        ProjectAssignmentDTOLight projectAssignmentDTO = new ProjectAssignmentDTOLight();
        if (projectAssignmentId != null) {
            projectAssignmentDTO.setId(projectAssignmentId);
        }
        projectAssignmentDTO.setProjectId(projectId);
        projectAssignmentDTO.setUserId(userId);
        projectAssignmentDTO.setValidFrom(validFrom);
        projectAssignmentDTO.setValidTo(validTo);
        projectAssignmentDTO.setProjectRoles(new HashSet<>(projectRoleDTOs));
        ProjectAssignmentDTOLight updatedProjectAssignmentDTO = dataAccessApi.createOrUpdateProjectAssignment(projectAssignmentDTO);
        return findById(updatedProjectAssignmentDTO.getId()).get();
    }

    private void checkConstraintsForCreateOrUpdate(Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo, List<ProjectRoleName> projectRoleNames, Integer projectAssignmentId) {
        Assert.notNull(projectId, "project id cannot be null");
        Assert.notNull(userId, "user id cannot be null");
        Assert.notNull(validFrom, "valid from cannot be null");
        if (validTo != null) {
            Assert.isTrue(validTo.isAfter(validFrom), "validTo must be after validFrom");
        }

        if (!memberRoleExists(projectRoleNames)) {
            throw new ProjectAssignmentException("MEMBER project role in project assignment must exist and cannot be removed");
        }

        userService.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        if (assignmentWithProjectAndUserAtGivenDateAlreadyExists(projectId, userId, validFrom, validTo, projectAssignmentId)) {
            throw new ProjectAssignmentException("Project assignment with user = " + userId + " and project = " + projectId + " already exists in given time from = " + validFrom.toString() + " and to = " + (validTo == null ? "[null]" : validTo.toString()));
        }
    }

    private boolean memberRoleExists(List<ProjectRoleName> projectRoleNames) {
        return projectRoleNames.stream().anyMatch(name -> name.equals(ProjectRoleName.MEMBER));
    }

    private boolean assignmentWithProjectAndUserAtGivenDateAlreadyExists(Integer projectId, Integer userId, LocalDate validFrom, LocalDate validTo, Integer projectAssignmentId) {
        List<ProjectAssignmentDTO> projectAssignments = dataAccessApi.findProjectAssignmentsByProjectIdAndUserId(projectId, userId);
        for (ProjectAssignmentDTO projectAssignment : projectAssignments) {
            if (projectAssignment.getId().equals(projectAssignmentId)) {
                continue;
            }

            if (projectAssignment.getValidTo() == null) {
                if (validTo == null || !validTo.isBefore(projectAssignment.getValidFrom())) {
                    return true;
                }
            } else if (!(validFrom.isAfter(projectAssignment.getValidTo()) || (validTo != null && validTo.isBefore(projectAssignment.getValidFrom())))) {
                return true;
            }
        }
        return false;
    }

    private ProjectAssignment map(ProjectAssignmentDTO projectAssignmentDTO) {
        ProjectAssignment projectAssignment = projectModelMapper.map(projectAssignmentDTO, ProjectAssignment.class);
        return projectAssignment;
    }

    private cz.cvut.fit.timetracking.data.api.dto.ProjectRoleName map(ProjectRoleName projectRoleName) {
        return projectModelMapper.map(projectRoleName, cz.cvut.fit.timetracking.data.api.dto.ProjectRoleName.class);
    }
}

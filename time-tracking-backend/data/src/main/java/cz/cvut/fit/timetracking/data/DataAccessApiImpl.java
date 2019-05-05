package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTOLight;
import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleName;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleName;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTO;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTOLight;
import cz.cvut.fit.timetracking.data.service.ProjectDataService;
import cz.cvut.fit.timetracking.data.service.ProjectRoleDataService;
import cz.cvut.fit.timetracking.data.service.UserDataService;
import cz.cvut.fit.timetracking.data.service.UserRoleDataService;
import cz.cvut.fit.timetracking.data.service.WorkRecordDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DataAccessApiImpl implements DataAccessApi {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserRoleDataService userRoleDataService;

    @Autowired
    private ProjectDataService projectDataService;

    @Autowired
    private ProjectRoleDataService projectRoleDataService;

    @Autowired
    private WorkRecordDataService workRecordDataService;

    @Override
    public Optional<UserDTO> findUserById(Integer id) {
        return userDataService.findById(id);
    }

    @Override
    public Optional<UserDTO> findUserByEmail(String email) {
        return userDataService.findByEmail(email);
    }

    @Override
    public UserDTO createOrUpdateUser(UserDTO user) {
        return userDataService.createOrUpdate(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userDataService.deleteById(id);
    }


    @Override
    public List<UserRoleDTO> findAllUserRolesByUserId(Integer id) {
        return userRoleDataService.findAllByUserId(id);
    }

    @Override
    public List<UserRoleDTO> findUserRolesByNameIn(List<UserRoleName> roleNames) {
        return userRoleDataService.findAllByNameIn(roleNames);
    }

    @Override
    public ProjectDTO createOrUpdateProject(ProjectDTO project) {
        return projectDataService.createOrUpdate(project);
    }

    @Override
    public List<ProjectDTO> findAllProjects() {
        return projectDataService.findAll();
    }

    @Override
    public Optional<ProjectDTO> findProjectById(Integer id) {
        return projectDataService.findById(id);
    }

    @Override
    public void deleteProjectById(Integer id) {
        projectDataService.deleteById(id);
    }

    @Override
    public List<ProjectDTO> findAllAssignedProjectsWhereValidTimeOverlapsByUserId(LocalDate date, Integer userId) {
        return projectDataService.findAllAssignedProjectsWhereValidTimeOverlapsByUserId(date, userId);
    }

    @Override
    public List<ProjectRoleDTO> findAllProjectRoles() {
        return projectRoleDataService.findAll();
    }

    @Override
    public Optional<ProjectRoleDTO> findProjectRoleByName(ProjectRoleName projectRoleName) {
        return projectRoleDataService.findByName(projectRoleName);
    }

    @Override
    public List<ProjectRoleDTO> findAllProjectRolesIn(List<ProjectRoleName> roleNames) {
        return projectRoleDataService.findByNameIn(roleNames);
    }

    @Override
    public List<ProjectAssignmentDTO> findProjectAssignmentsByProjectId(Integer projectId) {
        return projectDataService.findProjectAssignmentsByProjectId(projectId);
    }

    @Override
    public Optional<ProjectAssignmentDTO> findProjectAssignmentById(Integer id) {
        return projectDataService.findProjectAssignmentById(id);
    }

    @Override
    public void deleteProjectAssignmentById(Integer id) {
        projectDataService.deleteProjectAssignmentById(id);
    }

    @Override
    public List<ProjectAssignmentDTO> findProjectAssignmentsByProjectIdAndUserId(Integer projectId, Integer userId) {
        return projectDataService.findProjectAssignmentsByProjectIdAndUserId(projectId, userId);
    }

    @Override
    public ProjectAssignmentDTOLight createOrUpdateProjectAssignment(ProjectAssignmentDTOLight projectAssignmentDTO) {
        return projectDataService.createOrUpdateProjectAssignment(projectAssignmentDTO);
    }

    @Override
    public boolean workRecordTimesOverlapWithOtherUserRecords(LocalDateTime from, LocalDateTime to, Integer userId) {
        return workRecordDataService.recordTimesOverlapsWithOtherRecords(from, to, userId);
    }

    @Override
    public WorkRecordDTOLight createOrUpdateWorkRecord(WorkRecordDTOLight workRecordDTOLight) {
        return workRecordDataService.createOrUpdate(workRecordDTOLight);
    }

    @Override
    public WorkRecordDTO createOrUpdateWorkRecord(WorkRecordDTO workRecordDTO) {
        return workRecordDataService.createOrUpdate(workRecordDTO);
    }

    @Override
    public void deleteWorkRecordById(Integer id) {
        workRecordDataService.deleteById(id);
    }

    @Override
    public Optional<WorkRecordDTO> findWorkRecordById(Integer id) {
        return workRecordDataService.findById(id);
    }

    @Override
    public List<WorkRecordDTO> findAllWorkRecordsBetween(LocalDateTime fromInclusive, LocalDateTime toExclusive) {
        return workRecordDataService.findAllBetween(fromInclusive, toExclusive);
    }

    @Override
    public List<WorkRecordDTO> findAllWorkRecordsBetweenByUserId(LocalDateTime fromInclusive, LocalDateTime toExclusive, Integer userId) {
        return workRecordDataService.findAllBetweenByUserId(fromInclusive, toExclusive, userId);
    }


}

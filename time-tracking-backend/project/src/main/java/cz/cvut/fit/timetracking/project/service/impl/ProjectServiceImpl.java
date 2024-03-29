package cz.cvut.fit.timetracking.project.service.impl;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleDTO;
import cz.cvut.fit.timetracking.data.api.dto.WorkTypeDTO;
import cz.cvut.fit.timetracking.project.dto.Project;
import cz.cvut.fit.timetracking.project.dto.ProjectRole;
import cz.cvut.fit.timetracking.project.dto.ProjectRoleName;
import cz.cvut.fit.timetracking.project.dto.WorkType;
import cz.cvut.fit.timetracking.project.exception.ProjectNotFoundException;
import cz.cvut.fit.timetracking.project.mapper.ProjectModelMapper;
import cz.cvut.fit.timetracking.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private DataAccessApi dataAccessApi;

    @Autowired
    private ProjectModelMapper projectModelMapper;

    @Override
    public Project create(String name, String description, LocalDate start, LocalDate end, List<WorkType> workTypes) {
        Assert.isTrue(!name.isBlank(), "name of a project cannot be blank");
        Assert.notNull(start, "start of a project cannot be null");
        Project result = createOrUpdateProject(null, name, description, start, end, workTypes);
        return result;
    }

    @Override
    public Project update(Integer projectId, String name, String description, LocalDate start, LocalDate end, List<WorkType> workTypes) {
        Assert.isTrue(!name.isBlank(), "name of a project cannot be blank");
        Assert.notNull(start, "start of a project cannot be null");
        Assert.notNull(projectId, "project id cannot be null");
        dataAccessApi.findProjectById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        Project result = createOrUpdateProject(projectId, name, description, start, end, workTypes);
        return result;
    }

    private Project createOrUpdateProject(Integer projectId, String name, String description, LocalDate start, LocalDate end, List<WorkType> workTypes) {
        ProjectDTO projectDTO = new ProjectDTO();
        if (projectId != null) {
            projectDTO.setId(projectId);
        }
        projectDTO.setName(name);
        projectDTO.setDescription(description);
        projectDTO.setStart(start);
        projectDTO.setEnd(end);
        projectDTO.setWorkTypes(map(workTypes));
        projectDTO = dataAccessApi.createOrUpdateProject(projectDTO);
        return map(projectDTO);
    }

    @Override
    public List<Project> findAll() {
        List<ProjectDTO> projectDTOs = dataAccessApi.findAllProjects();
        List<Project> projects = projectDTOs.stream().map(this::map).collect(Collectors.toList());
        return projects;
    }

    @Override
    public Optional<Project> findById(Integer id) {
        Optional<ProjectDTO> optionalProjectDTO = dataAccessApi.findProjectById(id);
        Optional<Project> result = optionalProjectDTO.map(this::map);
        return result;
    }

    @Override
    public List<Project> findAllCurrentlyAssignedProjectsByUserId(Integer userId) {
        Assert.notNull(userId, "user id cannot be null");
        List<ProjectDTO> projectDTOs = dataAccessApi.findAllAssignedProjectsWhereValidTimeOverlapsByUserId(LocalDate.now(), userId);
        List<Project> projects = projectDTOs.stream().map(this::map).collect(Collectors.toList());
        return projects;
    }

    @Override
    public void deleteById(Integer id) {
        Optional<ProjectDTO> optionalProjectDTO = dataAccessApi.findProjectById(id);
        optionalProjectDTO.ifPresentOrElse(p -> dataAccessApi.deleteProjectById(id), () -> { throw new ProjectNotFoundException(id); });
    }

    @Override
    public Optional<ProjectRole> findProjectRoleByName(ProjectRoleName projectRoleName) {
        Optional<ProjectRoleDTO> projectRoleDTO = dataAccessApi.findProjectRoleByName(map(projectRoleName));
        return projectRoleDTO.map(this::map);
    }

    @Override
    public List<ProjectRole> findAllProjectRoles() {
        List<ProjectRoleDTO> projectRoleDTOs = dataAccessApi.findAllProjectRoles();
        List<ProjectRole> projectRoles = projectRoleDTOs.stream().map(this::map).collect(Collectors.toList());
        return projectRoles;
    }

    @Override
    public boolean isUserAssignedToProject(Integer userId, Integer projectId) {
        List<ProjectAssignmentDTO> projectAssignments = dataAccessApi.findProjectAssignmentsByProjectIdAndUserId(projectId, userId);
        var isAssigned = projectAssignments.stream().anyMatch(this::isAssignmentValid);
        return isAssigned;
    }

    @Override
    public boolean isUserAssignedToProjectAndHasAnyRole(Integer userId, Integer projectId, String... roles) {
        List<ProjectAssignmentDTO> projectAssignments = dataAccessApi.findProjectAssignmentsByProjectIdAndUserId(projectId, userId);
        var isAssigned = projectAssignments.stream().anyMatch(projectAssignmentDTO ->
                isAssignmentValid(projectAssignmentDTO)
                && projectAssignmentDTO.getProjectRoles().stream().anyMatch(roleDTO -> Arrays.asList(roles).contains(roleDTO.getName().toString())));
        return isAssigned;
    }

    private boolean isAssignmentValid(ProjectAssignmentDTO projectAssignmentDTO) {
        var now = LocalDate.now();
        return !now.isBefore(projectAssignmentDTO.getValidFrom()) && (projectAssignmentDTO.getValidTo() == null || !now.isAfter(projectAssignmentDTO.getValidTo()));
    }

    private List<WorkTypeDTO> map(List<WorkType> workTypes) {
        return workTypes.stream().map(w -> projectModelMapper.map(w, WorkTypeDTO.class)).collect(Collectors.toList());
    }

    private cz.cvut.fit.timetracking.data.api.dto.ProjectRoleName map(ProjectRoleName projectRoleName) {
        return projectModelMapper.map(projectRoleName, cz.cvut.fit.timetracking.data.api.dto.ProjectRoleName.class);
    }

    private ProjectRole map(ProjectRoleDTO projectRoleDTO) {
        ProjectRole projectRole = projectModelMapper.map(projectRoleDTO, ProjectRole.class);
        return projectRole;
    }

    private Project map(ProjectDTO projectDTO) {
        Project project = projectModelMapper.map(projectDTO, Project.class);
        return project;
    }
}

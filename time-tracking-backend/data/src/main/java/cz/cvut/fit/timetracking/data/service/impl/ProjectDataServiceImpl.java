package cz.cvut.fit.timetracking.data.service.impl;

import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectAssignmentDTOLight;
import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;
import cz.cvut.fit.timetracking.data.entity.Project;
import cz.cvut.fit.timetracking.data.entity.ProjectAssignment;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import cz.cvut.fit.timetracking.data.repository.ProjectAssignmentRepository;
import cz.cvut.fit.timetracking.data.repository.ProjectRepository;
import cz.cvut.fit.timetracking.data.service.ProjectDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectDataServiceImpl implements ProjectDataService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectAssignmentRepository projectAssignmentRepository;

    @Autowired
    private DataModelMapper dataModelMapper;

    @Override
    public ProjectDTO createOrUpdate(ProjectDTO project) {
        Project projectEntity = dataModelMapper.map(project, Project.class);
        projectEntity = projectRepository.save(projectEntity);
        ProjectDTO result = dataModelMapper.map(projectEntity, ProjectDTO.class);
        return result;
    }

    @Override
    public List<ProjectDTO> findAll() {
        List<Project> projects = projectRepository.findAll();
        List<ProjectDTO> result = projects.stream().map(p -> dataModelMapper.map(p, ProjectDTO.class)).collect(Collectors.toList());
        return result;
    }

    @Override
    public Optional<ProjectDTO> findById(Integer id) {
        Optional<Project> optionalProjectDTO = projectRepository.findById(id);
        Optional<ProjectDTO> result = optionalProjectDTO.map(p -> dataModelMapper.map(p, ProjectDTO.class));
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectAssignmentDTO> findProjectAssignmentsByProjectId(Integer projectId) {
        Optional<Project> project = projectRepository.findWithAssignmentsAndProjectRolesFetchedById(projectId);
        List<ProjectAssignmentDTO> projectAssignmentDTOs = project.map(this::mapProjectAssignments).orElse(new ArrayList<>());
        return projectAssignmentDTOs;
    }

    @Override
    public Optional<ProjectAssignmentDTO> findProjectAssignmentById(Integer id) {
        Optional<ProjectAssignment> project = projectAssignmentRepository.findById(id);
        return project.map(this::map);
    }

    @Override
    public List<ProjectAssignmentDTO> findProjectAssignmentsByProjectIdAndUserId(Integer projectId, Integer userId) {
        List<ProjectAssignment> projectAssignments = projectAssignmentRepository.findAllByProjectIdAndUserId(projectId, userId);
        return projectAssignments.stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    public ProjectAssignmentDTOLight createOrUpdateProjectAssignment(ProjectAssignmentDTOLight projectAssignmentDTOLight) {
        ProjectAssignment projectAssignment = dataModelMapper.map(projectAssignmentDTOLight, ProjectAssignment.class);
        projectAssignment = projectAssignmentRepository.save(projectAssignment);
        ProjectAssignmentDTOLight result = dataModelMapper.map(projectAssignment, ProjectAssignmentDTOLight.class);
        return result;
    }

    @Override
    public void deleteProjectAssignmentById(Integer id) {
        projectAssignmentRepository.deleteById(id);
    }

    private List<ProjectAssignmentDTO> mapProjectAssignments(Project project) {
        List<ProjectAssignmentDTO> projectAssignmentDTOs = project.getProjectAssignments().stream().map(this::map).collect(Collectors.toList());
        return projectAssignmentDTOs;
    }

    private ProjectAssignmentDTO map(ProjectAssignment projectAssignment) {
        return dataModelMapper.map(projectAssignment, ProjectAssignmentDTO.class);
    }
}

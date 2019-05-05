package cz.cvut.fit.timetracking.project.service.impl;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;
import cz.cvut.fit.timetracking.project.dto.Project;
import cz.cvut.fit.timetracking.project.exception.ProjectNotFoundException;
import cz.cvut.fit.timetracking.project.mapper.ProjectModelMapper;
import cz.cvut.fit.timetracking.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
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
    public Project create(String name, String description, LocalDate start, LocalDate end) {
        Assert.isTrue(!name.isBlank(), "name of a project cannot be blank");
        Assert.notNull(start, "start of a project cannot be null");
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(name);
        projectDTO.setDescription(description);
        projectDTO.setStart(start);
        projectDTO.setEnd(end);
        projectDTO = dataAccessApi.createOrUpdateProject(projectDTO);
        Project result = map(projectDTO);
        return result;
    }

    @Override
    public Project update(int projectId, String name, String description, LocalDate start, LocalDate end) {
        Assert.isTrue(!name.isBlank(), "name of a project cannot be blank");
        Assert.notNull(start, "start of a project cannot be null");
        ProjectDTO projectDTO = dataAccessApi.findProjectById(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
        projectDTO.setName(name);
        projectDTO.setDescription(description);
        projectDTO.setStart(start);
        projectDTO.setEnd(end);
        projectDTO = dataAccessApi.createOrUpdateProject(projectDTO);
        Project result = map(projectDTO);
        return result;
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
    public void deleteById(Integer id) {
        Optional<ProjectDTO> optionalProjectDTO = dataAccessApi.findProjectById(id);
        optionalProjectDTO.ifPresentOrElse(p -> dataAccessApi.deleteProjectById(id), () -> { throw new ProjectNotFoundException(id); });
    }

    private Project map(ProjectDTO projectDTO) {
        Project project = projectModelMapper.map(projectDTO, Project.class);
        return project;
    }
}

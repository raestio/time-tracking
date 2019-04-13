package cz.cvut.fit.timetracking.project.service.impl;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;
import cz.cvut.fit.timetracking.project.dto.Project;
import cz.cvut.fit.timetracking.project.exception.ProjectNotFoundException;
import cz.cvut.fit.timetracking.project.mapper.ProjectModelMapper;
import cz.cvut.fit.timetracking.project.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Project createOrUpdate(Project project) {
        ProjectDTO projectDTO = projectModelMapper.map(project, ProjectDTO.class);
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
        optionalProjectDTO.ifPresentOrElse(p -> dataAccessApi.deleteProjectById(id), () -> {
            throw new ProjectNotFoundException(id);
        });
    }

    private Project map(ProjectDTO projectDTO) {
        Project project = projectModelMapper.map(projectDTO, Project.class);
        return project;
    }
}

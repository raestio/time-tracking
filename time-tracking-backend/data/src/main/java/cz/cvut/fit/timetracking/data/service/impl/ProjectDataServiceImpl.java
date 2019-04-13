package cz.cvut.fit.timetracking.data.service.impl;

import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;
import cz.cvut.fit.timetracking.data.entity.Project;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import cz.cvut.fit.timetracking.data.repository.ProjectRepository;
import cz.cvut.fit.timetracking.data.service.ProjectDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectDataServiceImpl implements ProjectDataService {

    @Autowired
    private ProjectRepository projectRepository;

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
}

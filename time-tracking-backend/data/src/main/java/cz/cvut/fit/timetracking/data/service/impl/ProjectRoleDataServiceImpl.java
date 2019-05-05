package cz.cvut.fit.timetracking.data.service.impl;

import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleName;
import cz.cvut.fit.timetracking.data.entity.ProjectRole;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import cz.cvut.fit.timetracking.data.repository.ProjectRoleRepository;
import cz.cvut.fit.timetracking.data.service.ProjectRoleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectRoleDataServiceImpl implements ProjectRoleDataService {

    @Autowired
    private ProjectRoleRepository projectRoleRepository;

    @Autowired
    private DataModelMapper dataModelMapper;

    @Override
    public List<ProjectRoleDTO> findAll() {
        List<ProjectRole> projectRoles = projectRoleRepository.findAll();
        List<ProjectRoleDTO> projectRoleDTOs = projectRoles.stream().map(this::map).collect(Collectors.toList());
        return projectRoleDTOs;
    }

    @Override
    public Optional<ProjectRoleDTO> findByName(ProjectRoleName projectRoleName) {
        Optional<ProjectRole> projectRole = projectRoleRepository.findByName(map(projectRoleName));
        return projectRole.map(this::map);
    }

    @Override
    public List<ProjectRoleDTO> findByNameIn(List<ProjectRoleName> projectRoleNames) {
        List<ProjectRole> projectRoles = projectRoleRepository.findByNameIn(projectRoleNames.stream().map(this::map).collect(Collectors.toList()));
        return projectRoles.stream().map(this::map).collect(Collectors.toList());
    }

    private cz.cvut.fit.timetracking.data.entity.enums.ProjectRoleName map(ProjectRoleName projectRoleName) {
        return dataModelMapper.map(projectRoleName, cz.cvut.fit.timetracking.data.entity.enums.ProjectRoleName.class);
    }

    private ProjectRoleDTO map(ProjectRole role) {
        return dataModelMapper.map(role, ProjectRoleDTO.class);
    }
}

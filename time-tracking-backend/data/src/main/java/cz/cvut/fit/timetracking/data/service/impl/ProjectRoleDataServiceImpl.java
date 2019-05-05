package cz.cvut.fit.timetracking.data.service.impl;

import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleDTO;
import cz.cvut.fit.timetracking.data.entity.ProjectRole;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import cz.cvut.fit.timetracking.data.repository.ProjectRoleRepository;
import cz.cvut.fit.timetracking.data.service.ProjectRoleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        List<ProjectRoleDTO> projectRoleDTOs = projectRoles.stream().map(role -> dataModelMapper.map(role, ProjectRoleDTO.class)).collect(Collectors.toList());
        return projectRoleDTOs;
    }
}

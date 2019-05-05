package cz.cvut.fit.timetracking.data.service;

import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleDTO;
import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleName;

import java.util.List;
import java.util.Optional;

public interface ProjectRoleDataService {
    List<ProjectRoleDTO> findAll();
    Optional<ProjectRoleDTO> findByName(ProjectRoleName projectRoleName);
    List<ProjectRoleDTO> findByNameIn(List<ProjectRoleName> projectRoleNames);
}

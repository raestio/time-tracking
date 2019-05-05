package cz.cvut.fit.timetracking.data.service;

import cz.cvut.fit.timetracking.data.api.dto.ProjectRoleDTO;

import java.util.List;

public interface ProjectRoleDataService {
    List<ProjectRoleDTO> findAll();
}

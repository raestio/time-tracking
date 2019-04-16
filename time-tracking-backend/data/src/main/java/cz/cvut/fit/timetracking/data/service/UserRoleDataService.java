package cz.cvut.fit.timetracking.data.service;

import cz.cvut.fit.timetracking.data.api.dto.UserRoleDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleName;

import java.util.List;

public interface UserRoleDataService {
    List<UserRoleDTO> findAllByUserId(Integer id);
    List<UserRoleDTO> findAllByNameIn(List<UserRoleName> roleNames);
}

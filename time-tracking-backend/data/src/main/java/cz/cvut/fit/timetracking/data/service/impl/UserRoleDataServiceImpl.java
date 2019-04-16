package cz.cvut.fit.timetracking.data.service.impl;

import cz.cvut.fit.timetracking.data.api.dto.UserRoleDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleName;
import cz.cvut.fit.timetracking.data.entity.UserRole;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import cz.cvut.fit.timetracking.data.repository.UserRoleRepository;
import cz.cvut.fit.timetracking.data.service.UserRoleDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleDataServiceImpl implements UserRoleDataService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private DataModelMapper dataModelMapper;

    @Override
    public List<UserRoleDTO> findAllByUserId(Integer id) {
        List<UserRole> userRoles = userRoleRepository.findAllByUserId(id);
        List<UserRoleDTO> userRoleDTOs = userRoles.stream().map(this::map).collect(Collectors.toList());
        return userRoleDTOs;
    }

    @Override
    public List<UserRoleDTO> findAllByNameIn(List<UserRoleName> roleNames) {
        List<UserRole> userRoles = userRoleRepository.findAllByNameIn(roleNames.stream().map(this::mapRoleName).collect(Collectors.toList()));
        List<UserRoleDTO> userRoleDTOs = userRoles.stream().map(this::map).collect(Collectors.toList());
        return userRoleDTOs;
    }

    private UserRoleDTO map(UserRole u) {
        return dataModelMapper.map(u, UserRoleDTO.class);
    }

    private cz.cvut.fit.timetracking.data.entity.enums.UserRoleName mapRoleName(UserRoleName userRoleName) {
        return dataModelMapper.map(userRoleName, cz.cvut.fit.timetracking.data.entity.enums.UserRoleName.class);
    }
}

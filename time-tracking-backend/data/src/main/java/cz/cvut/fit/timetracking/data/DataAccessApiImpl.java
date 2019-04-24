package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleName;
import cz.cvut.fit.timetracking.data.service.ProjectDataService;
import cz.cvut.fit.timetracking.data.service.UserDataService;
import cz.cvut.fit.timetracking.data.service.UserRoleDataService;
import cz.cvut.fit.timetracking.data.service.WorkRecordDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DataAccessApiImpl implements DataAccessApi {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private UserRoleDataService userRoleDataService;

    @Autowired
    private ProjectDataService projectDataService;

    @Autowired
    private WorkRecordDataService workRecordDataService;

    @Override
    public Optional<UserDTO> findUserById(Integer id) {
        return userDataService.findById(id);
    }

    @Override
    public Optional<UserDTO> findUserByEmail(String email) {
        return userDataService.findByEmail(email);
    }

    @Override
    public UserDTO createOrUpdateUser(UserDTO user) {
        return userDataService.createOrUpdate(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userDataService.deleteById(id);
    }


    @Override
    public List<UserRoleDTO> findAllUserRolesByUserId(Integer id) {
        return userRoleDataService.findAllByUserId(id);
    }

    @Override
    public List<UserRoleDTO> findUserRolesByNameIn(List<UserRoleName> roleNames) {
        return userRoleDataService.findAllByNameIn(roleNames);
    }

    @Override
    public ProjectDTO createOrUpdateProject(ProjectDTO project) {
        return projectDataService.createOrUpdate(project);
    }

    @Override
    public List<ProjectDTO> findAllProjects() {
        return projectDataService.findAll();
    }

    @Override
    public Optional<ProjectDTO> findProjectById(Integer id) {
        return projectDataService.findById(id);
    }

    @Override
    public void deleteProjectById(Integer id) {
        projectDataService.deleteById(id);
    }

    @Override
    public boolean workRecordTimesOverlapWithOtherUserRecords(LocalDateTime from, LocalDateTime to, Integer userId) {
        return workRecordDataService.recordTimesOverlapsWithOtherRecords(from, to, userId);
    }
}

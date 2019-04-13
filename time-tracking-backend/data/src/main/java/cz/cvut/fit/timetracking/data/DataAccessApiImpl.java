package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.ProjectDTO;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.service.ProjectDataService;
import cz.cvut.fit.timetracking.data.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataAccessApiImpl implements DataAccessApi {

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private ProjectDataService projectDataService;

    @Override
    public Optional<UserDTO> findUserById(Integer id) {
        return userDataService.findById(id);
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
}

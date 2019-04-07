package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.entity.User;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import cz.cvut.fit.timetracking.data.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataAccessApiImpl implements DataAccessApi {

    @Autowired
    private UserDataService userService;

    @Autowired
    private DataModelMapper dataModelMapper;

    @Override
    public Optional<UserDTO> findUserById(Integer id) {
        Optional<User> userDAO = userService.findById(id);
        Optional<UserDTO> result = userDAO.map(user -> dataModelMapper.map(user, UserDTO.class));
        return result;
    }

    @Override
    public UserDTO createOrUpdateUser(UserDTO user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        User userDAO = dataModelMapper.map(user, User.class);
        userDAO = userService.createOrUpdateUser(userDAO);
        UserDTO result = dataModelMapper.map(userDAO, UserDTO.class);
        return result;
    }

    @Override
    public void deleteUserById(Integer id) {
        userService.deleteById(id);
    }
}

package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.entity.User;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import cz.cvut.fit.timetracking.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataAccessApiImpl implements DataAccessApi {

    @Autowired
    private UserService userService;

    @Autowired
    private DataModelMapper dataModelMapper;

    @Override
    public Optional<UserDTO> findUserById(Integer id) {
        Optional<User> userDAO = userService.findById(id);
        Optional<UserDTO> result = userDAO.map(user -> dataModelMapper.map(userDAO, UserDTO.class));
        return result;
    }

    @Override
    public UserDTO createOrUpdateUser(UserDTO user) {
        return null;
    }
}

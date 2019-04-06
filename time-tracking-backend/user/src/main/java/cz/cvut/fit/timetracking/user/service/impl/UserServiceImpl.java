package cz.cvut.fit.timetracking.user.service.impl;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.user.dto.User;
import cz.cvut.fit.timetracking.user.mapper.UserModelMapper;
import cz.cvut.fit.timetracking.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DataAccessApi dataAccessApi;

    @Autowired
    private UserModelMapper userModelMapper;

    @Override
    public Optional<User> findById(Integer id) {
        Optional<UserDTO> user = dataAccessApi.findUserById(id);
        Optional<User> result = user.map(u -> userModelMapper.map(u, User.class));
        return result;
    }

    @Override
    public User createOrUpdate(User user) {
        UserDTO userDTO = userModelMapper.map(user, UserDTO.class);
        userDTO = dataAccessApi.createOrUpdateUser(userDTO);
        User result = userModelMapper.map(userDTO, User.class);
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        dataAccessApi.deleteUserById(id);
    }
}

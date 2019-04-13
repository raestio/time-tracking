package cz.cvut.fit.timetracking.data.service.impl;

import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.entity.User;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import cz.cvut.fit.timetracking.data.repository.UserRepository;
import cz.cvut.fit.timetracking.data.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataServiceImpl implements UserDataService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DataModelMapper dataModelMapper;

    @Override
    public Optional<UserDTO> findById(Integer id) {
        Optional<User> userDAO = userRepository.findById(id);
        Optional<UserDTO> result = userDAO.map(user -> dataModelMapper.map(user, UserDTO.class));
        return result;
    }

    @Override
    public UserDTO createOrUpdate(UserDTO user) {
        User userEntity = dataModelMapper.map(user, User.class);
        userEntity = userRepository.save(userEntity);
        UserDTO result = dataModelMapper.map(userEntity, UserDTO.class);
        return result;
    }

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}

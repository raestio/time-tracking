package cz.cvut.fit.timetracking.data.service.impl;

import cz.cvut.fit.timetracking.data.entity.User;
import cz.cvut.fit.timetracking.data.repository.UserRepository;
import cz.cvut.fit.timetracking.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<User> findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @Override
    public User createOrUpdateUser(User user) {
        User u = userRepository.save(user);
        return u;
    }
}

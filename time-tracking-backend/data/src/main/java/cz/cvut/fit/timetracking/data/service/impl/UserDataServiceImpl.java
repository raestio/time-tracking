package cz.cvut.fit.timetracking.data.service.impl;

import cz.cvut.fit.timetracking.data.entity.User;
import cz.cvut.fit.timetracking.data.repository.UserRepository;
import cz.cvut.fit.timetracking.data.service.UserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDataServiceImpl implements UserDataService {

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

    @Override
    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}

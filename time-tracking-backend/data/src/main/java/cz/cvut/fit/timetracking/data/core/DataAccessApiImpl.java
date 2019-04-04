package cz.cvut.fit.timetracking.data.core;

import cz.cvut.fit.timetracking.data.api.DataAccessApi;
import cz.cvut.fit.timetracking.data.api.dto.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataAccessApiImpl implements DataAccessApi {

    @Override
    public Optional<User> findUserById(Integer id) {
        return Optional.empty();
    }
}

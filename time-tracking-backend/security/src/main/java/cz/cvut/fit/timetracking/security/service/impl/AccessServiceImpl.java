package cz.cvut.fit.timetracking.security.service.impl;

import cz.cvut.fit.timetracking.security.service.AccessService;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {

    @Override
    public boolean sameUser(Integer id1, Integer id2) {
        return id1.equals(id2);
    }
}

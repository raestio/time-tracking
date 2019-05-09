package cz.cvut.fit.timetracking.security.service;

public interface AccessService {
    boolean sameUser(Integer id1, Integer id2);
}

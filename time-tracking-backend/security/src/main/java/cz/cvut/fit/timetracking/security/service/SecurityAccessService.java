package cz.cvut.fit.timetracking.security.service;

public interface SecurityAccessService {
    boolean sameUser(Integer id1, Integer id2);
}

package cz.cvut.fit.timetracking.security.service;

public interface SecurityAccessService {
    boolean sameUser(Integer id1, Integer id2);

    boolean workRecordIsMineOrIam(Integer userId, Integer workRecordId, String role);
}

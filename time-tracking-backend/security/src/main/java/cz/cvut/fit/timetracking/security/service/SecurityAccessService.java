package cz.cvut.fit.timetracking.security.service;

public interface SecurityAccessService {
    boolean itIsMe(Integer userId);
    boolean hasProjectRole(Integer projectId, String role);
    boolean workRecordIsMineOrHasProjectRole(Integer workRecordId, String role);
}

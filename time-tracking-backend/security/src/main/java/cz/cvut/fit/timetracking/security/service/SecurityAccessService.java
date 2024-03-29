package cz.cvut.fit.timetracking.security.service;

public interface SecurityAccessService {
    boolean itIsMe(Integer userId);

    boolean hasAnyProjectRole(Integer projectId, String... roles);

    boolean hasProjectRole(Integer projectId, String role);

    boolean itIsMeOrNull(Integer userId);

    boolean workRecordIsMineOrHasProjectRole(Integer workRecordId, String role);
}

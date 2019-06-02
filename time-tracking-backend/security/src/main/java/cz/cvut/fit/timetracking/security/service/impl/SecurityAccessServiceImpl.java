package cz.cvut.fit.timetracking.security.service.impl;

import cz.cvut.fit.timetracking.project.service.ProjectService;
import cz.cvut.fit.timetracking.security.oauth2.UserPrincipal;
import cz.cvut.fit.timetracking.security.service.SecurityAccessService;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityAccessServiceImpl implements SecurityAccessService {

    @Autowired
    private WorkRecordService workRecordService;

    @Autowired
    private ProjectService projectService;

    @Override
    public boolean itIsMe(Integer userId) {
        return userPrincipal().getId().equals(userId);
    }

    @Override
    public boolean itIsMeOrNull(Integer userId) {
        return userId == null || itIsMe(userId);
    }

    @Override
    public boolean workRecordIsMineOrHasProjectRole(Integer workRecordId, String role) {
        var workRecordOptional = workRecordService.findById(workRecordId);
        if (workRecordOptional.isPresent()) {
            var workRecord = workRecordOptional.get();
            var userId = userPrincipal().getId();
            if (workRecord.getUser().getId().equals(userId)) {
                return true;
            }

            Integer projectId = workRecord.getProject().getId();
            return hasProjectRole(projectId, userId, role);
        }
        return true;
    }

    @Override
    public boolean hasProjectRole(Integer projectId, String role) {
        return hasProjectRole(projectId, userPrincipal().getId(), role);
    }

    private boolean hasProjectRole(Integer projectId, Integer userId, String role) {
        var hasRole = projectId != null && StringUtils.isNotBlank(role) && projectService.isUserAssignedToProjectAndHasRole(userId, projectId, role);
        return hasRole;
    }

    private UserPrincipal userPrincipal() {
        var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserPrincipal) principal;
    }
}

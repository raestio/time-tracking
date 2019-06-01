package cz.cvut.fit.timetracking.security.service.impl;

import cz.cvut.fit.timetracking.project.service.ProjectService;
import cz.cvut.fit.timetracking.security.service.SecurityAccessService;
import cz.cvut.fit.timetracking.workrecord.service.WorkRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecurityAccessServiceImpl implements SecurityAccessService {

    @Autowired
    private WorkRecordService workRecordService;

    @Autowired
    private ProjectService projectService;

    @Override
    public boolean sameUser(Integer id1, Integer id2) {
        return id1.equals(id2);
    }

    @Override
    public boolean workRecordIsMineOrIam(Integer userId, Integer workRecordId, String role) {
        var workRecordOptional = workRecordService.findById(workRecordId);
        if (workRecordOptional.isPresent()) {
            var workRecord = workRecordOptional.get();
            if (workRecord.getUser().getId().equals(userId)) {
                return true;
            }

            Integer projectId = workRecord.getProject().getId();
            var hasRole = projectService.isUserAssignedToProjectAndHasRole(userId, projectId, role);
            return hasRole;
        }
        return true;
    }
}

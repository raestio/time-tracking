package cz.cvut.fit.timetracking.data.utils;

import cz.cvut.fit.timetracking.data.api.dto.AuthProvider;
import cz.cvut.fit.timetracking.data.api.dto.UserDTO;
import cz.cvut.fit.timetracking.data.entity.Project;
import cz.cvut.fit.timetracking.data.entity.ProjectAssignment;
import cz.cvut.fit.timetracking.data.entity.User;
import cz.cvut.fit.timetracking.data.entity.UserRole;
import cz.cvut.fit.timetracking.data.entity.WorkRecord;
import cz.cvut.fit.timetracking.data.entity.WorkType;
import cz.cvut.fit.timetracking.data.entity.enums.AuthProviderEnum;
import cz.cvut.fit.timetracking.data.entity.enums.UserRoleName;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DataTestUtils {

    public static WorkRecord getWorkRecord(int projectId, int workTypeId, int userId, LocalDateTime from, LocalDateTime to) {
        WorkRecord workRecord = new WorkRecord();
        workRecord.setDateFrom(from);
        workRecord.setDateTo(to);
        workRecord.setDescription("test work");
        workRecord.setDateCreated(LocalDateTime.now());

        Project project = new Project();
        project.setId(projectId);
        workRecord.setProject(project);

        WorkType workType = new WorkType();
        workType.setId(workTypeId);
        workRecord.setWorkType(workType);

        User user = new User();
        user.setId(userId);
        workRecord.setUser(user);
        return workRecord;
    }

    public static Project getProject() {
        Project project = new Project();
        project.setName("Test project");
        project.setStart(LocalDate.now());
        return project;
    }

    public static WorkType getWorkType() {
        WorkType workType = new WorkType();
        workType.setName("Vyvoj");
        return workType;
    }

    public static UserDTO getUserDTO() {
        UserDTO user = new UserDTO();
        user.setName("Test");
        user.setSurname("Novy");
        user.setAuthProvider(AuthProvider.GOOGLE);
        user.setEmail("tmp@test__0.com");
        return user;
    }

    public static User getUser() {
        User user = new User();
        user.setName("Test");
        user.setSurname("Novy2");
        user.setAuthProvider(AuthProviderEnum.GOOGLE);
        user.setEmail("tmp@test__1.com");
        return user;
    }

    public static List<UserRole> getUserRoles() {
        List<UserRole> roles = new ArrayList<>();
        UserRole userRole1 = new UserRole();
        userRole1.setName(UserRoleName.USER);
        UserRole userRole2 = new UserRole();
        userRole2.setName(UserRoleName.ADMIN);
        roles.add(userRole1);
        roles.add(userRole2);
        return roles;
    }
}

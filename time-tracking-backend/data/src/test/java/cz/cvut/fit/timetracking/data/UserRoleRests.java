package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.configuration.DataTestsConfiguration;
import cz.cvut.fit.timetracking.data.api.dto.UserRoleDTO;
import cz.cvut.fit.timetracking.data.entity.User;
import cz.cvut.fit.timetracking.data.entity.UserRole;
import cz.cvut.fit.timetracking.data.repository.UserRepository;
import cz.cvut.fit.timetracking.data.repository.UserRoleRepository;
import cz.cvut.fit.timetracking.data.service.UserRoleDataService;
import cz.cvut.fit.timetracking.data.utils.UserTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserRoleRests extends DataTestsConfiguration {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleDataService userRoleDataService;

    @Before
    public void init() {
        List<UserRole> userRoles = UserTestUtils.getUserRoles();
        userRoles = userRoleRepository.saveAll(userRoles);
        User user = UserTestUtils.getUser();
        user.setUserRoles(new HashSet<>(userRoles));
        userRepository.save(user);
    }

    @After
    public void cleanUp() {
        userRepository.deleteAll();
        userRoleRepository.deleteAll();
    }

    @Test
    public void testDataServiceFindUserRoles() {
        User user = userRepository.findByEmail(UserTestUtils.getUser().getEmail()).get();
        List<UserRoleDTO> userRoleLit = userRoleDataService.findAllByUserId(user.getId());
        assertThat(userRoleLit.size()).isEqualTo(UserTestUtils.getUserRoles().size());
    }

    @Test
    public void testFetchedUserRoles() {
        User user = userRepository.findByEmail(UserTestUtils.getUser().getEmail()).get();
        assertThat(user.getUserRoles().size()).isEqualTo(UserTestUtils.getUserRoles().size());
    }

}

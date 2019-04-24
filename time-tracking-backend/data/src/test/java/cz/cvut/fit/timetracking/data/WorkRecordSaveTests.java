package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.configuration.DataTestsConfiguration;
import cz.cvut.fit.timetracking.data.entity.WorkRecord;
import cz.cvut.fit.timetracking.data.repository.ProjectRepository;
import cz.cvut.fit.timetracking.data.repository.WorkRecordRepository;
import cz.cvut.fit.timetracking.data.repository.WorkTypeRepository;
import cz.cvut.fit.timetracking.data.service.UserDataService;
import cz.cvut.fit.timetracking.data.utils.DataTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class WorkRecordSaveTests extends DataTestsConfiguration {

    @Autowired
    private WorkRecordRepository workRecordRepository;

    @Autowired
    private UserDataService userDataService;

    @Autowired
    private WorkTypeRepository workTypeRepository;

    @Autowired
    private ProjectRepository projectRepository;

    private int userId;
    private int projectId;
    private int workTypeId;

    @Before
    public void init() {
        userId = userDataService.createOrUpdate(DataTestUtils.getUserDTO()).getId();
        workTypeId = workTypeRepository.save(DataTestUtils.getWorkType()).getId();
        projectId = projectRepository.save(DataTestUtils.getProject()).getId();
    }

    @After
    public void cleanUp() {
        userDataService.deleteById(userId);
        workTypeRepository.deleteAll();
        projectRepository.deleteAll();
    }

    @Test
    public void testSaveWorkRecord() {
        WorkRecord workRecord = DataTestUtils.getWorkRecord(projectId, workTypeId, userId, LocalDateTime.now(), LocalDateTime.now().plusHours(3));
        workRecord = workRecordRepository.save(workRecord);
        workRecord = workRecordRepository.findById(workRecord.getId()).get();
        assertThat(workRecord.getId()).isNotNull();
        assertThat(workRecord.getUser().getName()).isEqualTo(DataTestUtils.getUserDTO().getName());
        assertThat(workRecord.getWorkType().getName()).isEqualTo(DataTestUtils.getWorkType().getName());
        assertThat(workRecord.getProject().getName()).isEqualTo(DataTestUtils.getProject().getName());
        workRecordRepository.deleteById(workRecord.getId());
        assertThat(workRecordRepository.findById(workRecord.getId())).isEmpty();
    }



}

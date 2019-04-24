package cz.cvut.fit.timetracking.data.api;

import cz.cvut.fit.timetracking.configuration.DataAccessApiTestsConfiguration;
import cz.cvut.fit.timetracking.data.api.dto.AuthProvider;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTO;
import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTOLight;
import cz.cvut.fit.timetracking.data.entity.WorkRecord;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "/sql_initialization_test_scripts/work_record_overlap_tests_insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql_initialization_test_scripts/work_record_overlap_tests_delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class WorkRecordTests extends DataAccessApiTestsConfiguration {
    private final LocalDateTime START = LocalDateTime.parse("2019-04-24T05:00:00");
    private final LocalDateTime END = LocalDateTime.parse("2019-04-24T06:00:00");
    private final int USER_ID = -1;

    @Autowired
    private DataAccessApi dataAccessApi;

    @Test
    public void test_WhenTimesInMiddle_expectOverlapTrue() {
        assertThat(dataAccessApi.workRecordTimesOverlapWithOtherUserRecords(START.plusMinutes(10), END.minusMinutes(10), USER_ID)).isTrue();
    }

    @Test
    public void test_WhenStartTimeInMiddle_expectOverlapTrue() {
        assertThat(dataAccessApi.workRecordTimesOverlapWithOtherUserRecords(START.plusMinutes(30), END.plusMinutes(30), USER_ID)).isTrue();
    }

    @Test
    public void test_WhenEndTimeInMiddle_expectOverlapTrue() {
        assertThat(dataAccessApi.workRecordTimesOverlapWithOtherUserRecords(START.minusMinutes(30), END.minusMinutes(30), USER_ID)).isTrue();
    }

    @Test
    public void test_StartTimeBeforeAndEndTimeAfter_expectOverlapTrue() {
        assertThat(dataAccessApi.workRecordTimesOverlapWithOtherUserRecords(START.minusMinutes(30), END.plusMinutes(30), USER_ID)).isTrue();
    }

    @Test
    public void test_timesBefore_expectOverlapFalse() {
        assertThat(dataAccessApi.workRecordTimesOverlapWithOtherUserRecords(START.minusMinutes(60), START.minusMinutes(10), USER_ID)).isFalse();
    }

    @Test
    public void test_timesAfter_expectOverlapFalse() {
        assertThat(dataAccessApi.workRecordTimesOverlapWithOtherUserRecords(END.plusMinutes(10), END.plusMinutes(60), USER_ID)).isFalse();
    }

    @Test
    public void test_startTimeBeginAtEndOfAnotherTime_expectOverlapFalse() {
        assertThat(dataAccessApi.workRecordTimesOverlapWithOtherUserRecords(END, END.plusMinutes(60), USER_ID)).isFalse();
        assertThat(dataAccessApi.workRecordTimesOverlapWithOtherUserRecords(START.minusMinutes(60), START, USER_ID)).isFalse();
    }

    @Test
    public void test_createWorkRecordAndDelete() {
        var dateCreated = LocalDateTime.now();
        var descr = "popis prace";
        WorkRecordDTOLight workRecordDTOLight = new WorkRecordDTOLight();
        workRecordDTOLight.setDateFrom(END);
        workRecordDTOLight.setDateTo(END.plusMinutes(60));
        workRecordDTOLight.setDateCreated(dateCreated);
        workRecordDTOLight.setDescription(descr);
        workRecordDTOLight.setProjectId(-1);
        workRecordDTOLight.setUserId(-1);
        workRecordDTOLight.setWorkTypeId(-1);
        WorkRecordDTOLight savedWorkRecord = dataAccessApi.createOrUpdateWorkRecord(workRecordDTOLight);
        assertThat(savedWorkRecord.getId()).isNotNull();
        assertThat(savedWorkRecord.getDateCreated()).isEqualTo(dateCreated);
        assertThat(savedWorkRecord.getDateFrom()).isEqualTo(END);
        assertThat(savedWorkRecord.getDateTo()).isEqualTo(END.plusMinutes(60));
        assertThat(savedWorkRecord.getDescription()).isEqualTo(descr);
        assertThat(savedWorkRecord.getWorkTypeId()).isEqualTo(-1);
        assertThat(savedWorkRecord.getUserId()).isEqualTo(-1);
        assertThat(savedWorkRecord.getProjectId()).isEqualTo(-1);
        dataAccessApi.deleteWorkRecordById(savedWorkRecord.getId());
        assertThat(dataAccessApi.findWorkRecordById(savedWorkRecord.getId())).isEmpty();
    }

    @Test
    public void test_updateWorkRecord() {
        WorkRecordDTO workRecord = dataAccessApi.findWorkRecordById(-1).get();
        workRecord.setDescription("nove");
        workRecord.setDateUpdated(LocalDateTime.now());
        workRecord.setDateFrom(END);
        workRecord.setDateTo(END.plusMinutes(60));
        WorkRecordDTO updated = dataAccessApi.createOrUpdateWorkRecord(workRecord);
        assertThat(updated.getId()).isEqualTo(workRecord.getId());
        assertThat(updated.getDescription()).isEqualTo("nove");
        assertThat(updated.getDateFrom()).isEqualTo(END);
        assertThat(updated.getDateTo()).isEqualTo(END.plusMinutes(60));
        assertThat(updated.getUser().getId()).isEqualTo(-1);
        assertThat(updated.getUser().getEmail()).isEqualTo("tmp@ahoj.cau");
        assertThat(updated.getUser().getAuthProvider()).isEqualTo(AuthProvider.GOOGLE);
        assertThat(updated.getProject().getId()).isEqualTo(-1);
        assertThat(updated.getProject().getName()).isEqualTo("test project");
        assertThat(updated.getWorkType().getId()).isEqualTo(-1);
        assertThat(updated.getWorkType().getName()).isEqualTo("test work type");
    }
}

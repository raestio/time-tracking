package cz.cvut.fit.timetracking.data;

import cz.cvut.fit.timetracking.configuration.DataTestsConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "/sql_initialization_test_scripts/work_record_overlap_tests_insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql_initialization_test_scripts/work_record_overlap_tests_delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class WorkRecordOverlapTests extends DataTestsConfiguration {
    private final LocalDateTime START = LocalDateTime.parse("2019-04-24T05:00:00");
    private final LocalDateTime END = LocalDateTime.parse("2019-04-24T06:00:00");
    private final int USER_ID = -1;

    @Autowired
    private DataAccessApiImpl dataAccessApi;

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
}

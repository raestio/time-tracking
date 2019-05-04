package cz.cvut.fit.timetracking.report.helper;

import cz.cvut.fit.timetracking.report.dto.DayReportItem;
import cz.cvut.fit.timetracking.report.dto.ProjectReportItem;
import cz.cvut.fit.timetracking.report.dto.WorkReportItem;
import cz.cvut.fit.timetracking.workrecord.dto.Project;
import cz.cvut.fit.timetracking.workrecord.dto.User;
import cz.cvut.fit.timetracking.workrecord.dto.WorkRecord;
import cz.cvut.fit.timetracking.workrecord.dto.WorkType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReportServiceTestsHelper {
    public static final int MINUTES_WORK_RECORD_DIFF = 90;
    public static final LocalDateTime WORK_RECORD_1_FROM = LocalDateTime.parse("2019-02-04T10:00");
    public static final LocalDateTime WORK_RECORD_2_FROM = LocalDateTime.parse("2018-03-20T18:15");
    public static final LocalDateTime WORK_RECORD_3_FROM = LocalDateTime.parse("2019-02-07T08:30");


    private ReportServiceTestsHelper() {

    }

    public static int countTotalSpentTime(List<ProjectReportItem> projectReportItems) {
        int minutes = 0;
        for (ProjectReportItem projectReportItem : projectReportItems) {
            minutes += countTotalSpentTimeWorkItems(projectReportItem.getWorkReportItems());
        }
        return minutes;
    }

    public static int countTotalSpentTimeWorkItems(List<WorkReportItem> workReportItems) {
        int minutes = 0;
        for (WorkReportItem workReportItem : workReportItems) {
            minutes += workReportItem.getMinutesSpent();
        }
        return minutes;
    }

    public static List<WorkRecord> createWorkRecordsTestCase() {
        List<WorkRecord> workRecords = new ArrayList<>();
        workRecords.add(createWorkRecord1TestCase());
        workRecords.add(createWorkRecord2TestCase());
        workRecords.add(createWorkRecord3TestCase());
        workRecords.add(createWorkRecord4TestCase());
        workRecords.add(createWorkRecord5TestCase());
        return workRecords;
    }

    public static List<WorkRecord> createWorkRecordsTestCaseUserId1() {
        List<WorkRecord> workRecords = new ArrayList<>();
        workRecords.add(createWorkRecord1TestCase());
        workRecords.add(createWorkRecord3TestCase());
        workRecords.add(createWorkRecord4TestCase());
        workRecords.add(createWorkRecord5TestCase());
        return workRecords;
    }

    public static List<WorkRecord> createWorkRecordsTestCaseUserId2() {
        List<WorkRecord> workRecords = new ArrayList<>();
        workRecords.add(createWorkRecord2TestCase());
        return workRecords;
    }

    private static WorkRecord createWorkRecord5TestCase() {
        return new WorkRecord(5,WORK_RECORD_1_FROM.plusMinutes(2 * MINUTES_WORK_RECORD_DIFF), WORK_RECORD_1_FROM.plusMinutes(3 * MINUTES_WORK_RECORD_DIFF), "dd", createProject2TestCase(), createWorkType1TestCase(), createUser1TestCase());
    }

    private static WorkRecord createWorkRecord4TestCase() {
        return new WorkRecord(1, WORK_RECORD_1_FROM, WORK_RECORD_1_FROM.plusMinutes(MINUTES_WORK_RECORD_DIFF), "dd", createProject1TestCase(), createWorkType1TestCase(), createUser1TestCase());
    }

    private static WorkRecord createWorkRecord1TestCase() {
        return new WorkRecord(2, WORK_RECORD_1_FROM.plusMinutes(MINUTES_WORK_RECORD_DIFF), WORK_RECORD_1_FROM.plusMinutes(2 * MINUTES_WORK_RECORD_DIFF), "dd", createProject1TestCase(), createWorkType1TestCase(), createUser1TestCase());
    }

    private static WorkRecord createWorkRecord2TestCase() {
        return new WorkRecord(3, WORK_RECORD_2_FROM, WORK_RECORD_2_FROM.plusMinutes(MINUTES_WORK_RECORD_DIFF), "adasddd", createProject1TestCase(), createWorkType2TestCase(), createUser2TestCase());

    }

    private static WorkRecord createWorkRecord3TestCase() {
        return new WorkRecord(4, WORK_RECORD_3_FROM, WORK_RECORD_3_FROM.plusMinutes(MINUTES_WORK_RECORD_DIFF), "dasdasdewfd", createProject2TestCase(), createWorkType1TestCase(), createUser1TestCase());
    }

    private static Project createProject1TestCase() {
        return new Project(1, "Google");
    }

    private static Project createProject2TestCase() {
        return new Project(2, "Tesla");
    }

    private static WorkType createWorkType1TestCase() {
        return new WorkType(1, "vyvoj");
    }

    private static WorkType createWorkType2TestCase() {
        return new WorkType(2, "analyza");
    }

    private static User createUser1TestCase() {
        return new User(1, "test", "testovic");
    }

    private static User createUser2TestCase() {
        return new User(2, "druhy", "test");
    }
}

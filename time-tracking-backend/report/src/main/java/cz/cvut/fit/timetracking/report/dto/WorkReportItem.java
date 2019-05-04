package cz.cvut.fit.timetracking.report.dto;

public class WorkReportItem {

    private WorkType workType;
    private Integer minutesSpent;

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public Integer getMinutesSpent() {
        return minutesSpent;
    }

    public void setMinutesSpent(Integer minutesSpent) {
        this.minutesSpent = minutesSpent;
    }
}

package cz.cvut.fit.timetracking.workrecord.dto;

public class WorkRecordConflictInfo {

    private WorkRecord workRecord;
    private Boolean isInConflict;

    public WorkRecord getWorkRecord() {
        return workRecord;
    }

    public void setWorkRecord(WorkRecord workRecord) {
        this.workRecord = workRecord;
    }

    public Boolean getInConflict() {
        return isInConflict;
    }

    public void setInConflict(Boolean inConflict) {
        isInConflict = inConflict;
    }
}

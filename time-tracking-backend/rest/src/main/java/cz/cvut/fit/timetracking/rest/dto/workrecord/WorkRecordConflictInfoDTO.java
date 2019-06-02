package cz.cvut.fit.timetracking.rest.dto.workrecord;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WorkRecordConflictInfoDTO {

    @JsonProperty("workRecord")
    private WorkRecordDTO workRecord;

    @JsonProperty("isInConflict")
    private Boolean isInConflict;

    public WorkRecordDTO getWorkRecord() {
        return workRecord;
    }

    public void setWorkRecord(WorkRecordDTO workRecord) {
        this.workRecord = workRecord;
    }

    public Boolean getInConflict() {
        return isInConflict;
    }

    public void setInConflict(Boolean inConflict) {
        isInConflict = inConflict;
    }
}

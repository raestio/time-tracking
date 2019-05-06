package cz.cvut.fit.timetracking.rest.dto.workrecord.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.workrecord.WorkRecordDTO;

import java.util.ArrayList;
import java.util.List;

public class WorkRecordsResponse {

    @JsonProperty("workRecords")
    private List<WorkRecordDTO> workRecords = new ArrayList<>();

    public List<WorkRecordDTO> getWorkRecords() {
        return workRecords;
    }

    public void setWorkRecords(List<WorkRecordDTO> workRecords) {
        this.workRecords = workRecords;
    }
}

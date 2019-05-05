package cz.cvut.fit.timetracking.rest.dto.search.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.search.WorkRecordSearchDTO;

import java.util.ArrayList;
import java.util.List;

public class SearchWorkRecordsResponse {

    @JsonProperty("workRecords")
    private List<WorkRecordSearchDTO> workRecords = new ArrayList<>();

    public SearchWorkRecordsResponse(List<WorkRecordSearchDTO> workRecords) {
        this.workRecords = workRecords;
    }

    public SearchWorkRecordsResponse() {

    }

    public List<WorkRecordSearchDTO> getWorkRecords() {
        return workRecords;
    }

    public void setWorkRecords(List<WorkRecordSearchDTO> workRecords) {
        this.workRecords = workRecords;
    }
}

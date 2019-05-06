package cz.cvut.fit.timetracking.rest.dto.workrecord.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class CreateWorkRecordsBulkRequest {

    @JsonProperty("requests")
    private List<CreateOrUpdateWorkRecordRequest> requests = new ArrayList<>();

    public List<CreateOrUpdateWorkRecordRequest> getRequests() {
        return requests;
    }

    public void setRequests(List<CreateOrUpdateWorkRecordRequest> requests) {
        this.requests = requests;
    }
}

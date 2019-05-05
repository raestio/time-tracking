package cz.cvut.fit.timetracking.rest.dto.project.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.project.WorkTypeDTO;

import java.util.ArrayList;
import java.util.List;

public class WorkTypesResponse {

    @JsonProperty("workTypes")
    private List<WorkTypeDTO> workTypes = new ArrayList<>();

    public List<WorkTypeDTO> getWorkTypes() {
        return workTypes;
    }

    public void setWorkTypes(List<WorkTypeDTO> workTypes) {
        this.workTypes = workTypes;
    }
}

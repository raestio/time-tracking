package cz.cvut.fit.timetracking.rest.dto.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import cz.cvut.fit.timetracking.rest.dto.project.WorkTypeDTO;

public class WorkReportItemDTO {

    @JsonProperty("workType")
    private WorkTypeDTO workType;

    @JsonProperty("minutesSpent")
    private Integer minutesSpent;

    public WorkTypeDTO getWorkType() {
        return workType;
    }

    public void setWorkType(WorkTypeDTO workType) {
        this.workType = workType;
    }

    public Integer getMinutesSpent() {
        return minutesSpent;
    }

    public void setMinutesSpent(Integer minutesSpent) {
        this.minutesSpent = minutesSpent;
    }
}

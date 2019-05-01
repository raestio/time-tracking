package cz.cvut.fit.timetracking.search.component;

import cz.cvut.fit.timetracking.search.constants.ElasticsearchProjectFieldNames;
import cz.cvut.fit.timetracking.search.constants.ElasticsearchUserFieldNames;
import cz.cvut.fit.timetracking.search.constants.ElasticsearchWorkRecordFieldNames;
import cz.cvut.fit.timetracking.search.dto.ProjectDocument;
import cz.cvut.fit.timetracking.search.dto.UserDocument;
import cz.cvut.fit.timetracking.search.dto.WorkRecordDocument;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Map;

@Component
public class ElasticsearchDocumentsMapper {

    public UserDocument mapHitToUser(SearchHit searchHit) {
        Map<String, Object> source = searchHit.getSourceAsMap();
        UserDocument userDocument = new UserDocument();
        userDocument.setId(toInt(source.get(ElasticsearchUserFieldNames.ID)));
        userDocument.setName(toStr(source.get(ElasticsearchUserFieldNames.NAME)));
        userDocument.setSurname(toStr(source.get(ElasticsearchUserFieldNames.SURNAME)));
        userDocument.setEmail(toStr(source.get(ElasticsearchUserFieldNames.EMAIL)));
        userDocument.setPictureUrl(toStr(source.get(ElasticsearchUserFieldNames.PICTURE_URL)));
        return userDocument;
    }

    public ProjectDocument mapHitToProject(SearchHit searchHit) {
        Map<String, Object> source = searchHit.getSourceAsMap();
        ProjectDocument project = new ProjectDocument();
        project.setId(toInt(source.get(ElasticsearchProjectFieldNames.ID)));
        project.setName(toStr(source.get(ElasticsearchProjectFieldNames.NAME)));
        project.setDescription(toStr(source.get(ElasticsearchProjectFieldNames.DESCRIPTION)));
        project.setStart(toLocalDate(source.get(ElasticsearchProjectFieldNames.START)));
        project.setEnd(toLocalDate(source.get(ElasticsearchProjectFieldNames.END)));
        return project;
    }

    public WorkRecordDocument mapHitToWorkRecord(SearchHit searchHit) {
        Map<String, Object> source = searchHit.getSourceAsMap();
        WorkRecordDocument workRecord = new WorkRecordDocument();
        workRecord.setId(toInt(source.get(ElasticsearchWorkRecordFieldNames.ID)));
        workRecord.setIdProject(toInt(source.get(ElasticsearchWorkRecordFieldNames.ID_PROJECT)));
        workRecord.setIdUser(toInt(source.get(ElasticsearchWorkRecordFieldNames.ID_USER)));
        workRecord.setDateFrom(toLocalDateTime(source.get(ElasticsearchWorkRecordFieldNames.DATE_FROM)));
        workRecord.setDateTo(toLocalDateTime(source.get(ElasticsearchWorkRecordFieldNames.DATE_TO)));
        workRecord.setDescription(toStr(source.get(ElasticsearchWorkRecordFieldNames.DESCRIPTION)));
        workRecord.setDateCreated(toLocalDateTime(source.get(ElasticsearchWorkRecordFieldNames.DATE_CREATED)));
        workRecord.setDateUpdated(toLocalDateTime(source.get(ElasticsearchWorkRecordFieldNames.DATE_UPDATED)));
        return workRecord;
    }

    private LocalDateTime toLocalDateTime(Object o) {
        if (o == null) {
            return null;
        }
        String strDate = toStr(o);
        LocalDateTime localDateTime = ZonedDateTime.parse(strDate).toLocalDateTime();
        return localDateTime;
    }

    private LocalDate toLocalDate(Object o) {
        if (o == null) {
            return null;
        }
        return toLocalDateTime(o).toLocalDate();
    }

    private String toStr(Object o) {
        return o == null ? null : String.valueOf(o);
    }

    private Integer toInt(Object o) {
        return o == null ? null : (Integer) o;
    }
}

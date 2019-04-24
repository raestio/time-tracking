package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.data.api.dto.WorkRecordDTOLight;
import cz.cvut.fit.timetracking.data.constants.PackageNames;
import cz.cvut.fit.timetracking.data.entity.Project;
import cz.cvut.fit.timetracking.data.entity.User;
import cz.cvut.fit.timetracking.data.entity.WorkRecord;
import cz.cvut.fit.timetracking.data.entity.WorkType;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import org.modelmapper.Converter;
import org.modelmapper.ExpressionMap;
import org.modelmapper.TypeMap;
import org.modelmapper.builder.ConfigurableMapExpression;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.DestinationSetter;
import org.modelmapper.spi.MappingContext;
import org.modelmapper.spi.SourceGetter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@Import(DatabaseConfiguration.class)
@ComponentScan(basePackages = PackageNames.DATA_CORE)
@EnableTransactionManagement
public class DataConfiguration {

    @Bean
    public DataModelMapper dataModelMapper() {
        DataModelMapper dataModelMapper = new DataModelMapper();
        dataModelMapper.getConfiguration().setAmbiguityIgnored(false);
        dataModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        addTypeMaps(dataModelMapper);
        return dataModelMapper;
    }

    private void addTypeMaps(DataModelMapper dataModelMapper) {
        typeMapWorkRecordDTOLightToWorkRecord(dataModelMapper);
        typeMapWorkRecordToWorkRecordDTOLight(dataModelMapper);
    }

    private void typeMapWorkRecordToWorkRecordDTOLight(DataModelMapper dataModelMapper) {
        TypeMap<WorkRecord, WorkRecordDTOLight> typeMap = dataModelMapper.createTypeMap(WorkRecord.class, WorkRecordDTOLight.class);
        typeMap.addMapping(source -> source.getProject().getId(), WorkRecordDTOLight::setProjectId);
        typeMap.addMapping(source -> source.getUser().getId(), WorkRecordDTOLight::setUserId);
        typeMap.addMapping(source -> source.getWorkType().getId(), WorkRecordDTOLight::setWorkTypeId);
    }

    private void typeMapWorkRecordDTOLightToWorkRecord(DataModelMapper dataModelMapper) {
        TypeMap<WorkRecordDTOLight, WorkRecord> typeMap = dataModelMapper.typeMap(WorkRecordDTOLight.class, WorkRecord.class);
        Converter<Integer, Project> projectConverter = context -> new Project(context.getSource());
        typeMap.addMappings(mapping -> mapping.using(projectConverter).map(WorkRecordDTOLight::getProjectId, WorkRecord::setProject));

        Converter<Integer, User> userConverter = context -> new User(context.getSource());
        typeMap.addMappings(mapping -> mapping.using(userConverter).map(WorkRecordDTOLight::getUserId, WorkRecord::setUser));

        Converter<Integer, WorkType> workTypeConverter = context -> new WorkType(context.getSource());
        typeMap.addMappings(mapping -> mapping.using(workTypeConverter).map(WorkRecordDTOLight::getWorkTypeId, WorkRecord::setWorkType));
    }
}

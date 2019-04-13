package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.workrecord.constants.PackageNames;
import cz.cvut.fit.timetracking.workrecord.mapper.WorkRecordModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(PackageNames.WORK_RECORD_CORE)
public class WorkRecordConfiguration {

    @Bean
    public WorkRecordModelMapper workRecordModelMapper() {
        WorkRecordModelMapper workRecordModelMapper = new WorkRecordModelMapper();
        workRecordModelMapper.getConfiguration().setAmbiguityIgnored(false);
        workRecordModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return workRecordModelMapper;
    }
}

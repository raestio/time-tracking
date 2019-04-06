package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.data.constants.PackageNames;
import cz.cvut.fit.timetracking.data.mapper.DataModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

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
        return dataModelMapper;
    }
}

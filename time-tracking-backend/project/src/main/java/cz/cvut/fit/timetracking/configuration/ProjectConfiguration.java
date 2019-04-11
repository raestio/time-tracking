package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.project.constants.PackageNames;
import cz.cvut.fit.timetracking.project.mapper.ProjectModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(PackageNames.PROJECT_CORE)
public class ProjectConfiguration {

    @Bean
    public ProjectModelMapper projectModelMapper() {
        ProjectModelMapper projectModelMapper = new ProjectModelMapper();
        projectModelMapper.getConfiguration().setAmbiguityIgnored(false);
        projectModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return projectModelMapper;
    }
}

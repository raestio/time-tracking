package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.rest.constants.PackageNames;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = PackageNames.REST_CORE)
@Import(SwaggerConfiguration.class)
public class RestConfiguration {

    @Bean
    public RestModelMapper restModelMapper() {
        RestModelMapper restModelMapper = new RestModelMapper();
        restModelMapper.getConfiguration().setAmbiguityIgnored(false);
        restModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return restModelMapper;
    }
}

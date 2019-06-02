package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.configuration.documentation.SwaggerConfiguration;
import cz.cvut.fit.timetracking.configuration.security.RestSecurityConfiguration;
import cz.cvut.fit.timetracking.rest.constants.PackageNames;
import cz.cvut.fit.timetracking.rest.mapper.RestModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = PackageNames.REST_CORE)
@Import({SwaggerConfiguration.class, RestSecurityConfiguration.class})
public class RestConfiguration implements WebMvcConfigurer {

    private final static int MAX_AGE_SECS = 3600;

    @Bean
    public RestModelMapper restModelMapper() {
        RestModelMapper restModelMapper = new RestModelMapper();
        restModelMapper.getConfiguration().setAmbiguityIgnored(false);
        restModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return restModelMapper;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS);
    }
}

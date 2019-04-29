package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.search.constants.PackageNames;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(PackageNames.SEARCH_CORE)
@Import(ElasticsearchConfiguration.class)
public class SearchConfiguration {
}

package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.data.constants.PackageNames;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import(DatabaseConfiguration.class)
@ComponentScan(basePackages = PackageNames.DATA_CORE)
@EnableTransactionManagement
public class DataConfiguration {
}

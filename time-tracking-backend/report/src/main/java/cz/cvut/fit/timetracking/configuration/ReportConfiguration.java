package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.report.constants.PackageNames;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(PackageNames.REPORT_CORE)
public class ReportConfiguration {



}

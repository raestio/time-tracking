package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.security.constants.PackageNames;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@ComponentScan(PackageNames.SECURITY_CORE)
public class BaseSecurityConfiguration {


}

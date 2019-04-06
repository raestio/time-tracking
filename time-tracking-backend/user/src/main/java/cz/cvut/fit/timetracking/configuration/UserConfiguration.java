package cz.cvut.fit.timetracking.configuration;

import cz.cvut.fit.timetracking.user.constants.PackageNames;
import cz.cvut.fit.timetracking.user.mapper.UserModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = PackageNames.USER_CORE)
public class UserConfiguration {

    @Bean
    public UserModelMapper userModelMapper() {
        UserModelMapper userModelMapper = new UserModelMapper();
        userModelMapper.getConfiguration().setAmbiguityIgnored(false);
        userModelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return userModelMapper;
    }
}

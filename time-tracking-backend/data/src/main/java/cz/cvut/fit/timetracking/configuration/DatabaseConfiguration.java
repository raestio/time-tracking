package cz.cvut.fit.timetracking.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import cz.cvut.fit.timetracking.data.constants.PackageNames;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableJpaRepositories(
        basePackages = PackageNames.DATABASE_TIME_TRACKING_REPOSITORY,
        entityManagerFactoryRef = DatabaseConfiguration.ENTITY_MANAGER_FACTORY_METHOD_NAME,
        transactionManagerRef = DatabaseConfiguration.TRANSACTION_MANAGER_METHOD_NAME
)
public class DatabaseConfiguration {

    /**
     * Name of persistence unit, you need this name if you want to use specific persistence manager
     * e.g. in @{@link org.springframework.transaction.annotation.Transactional} annotation.
     */
    public static final String PERSISTENCE_UNIT_NAME = "timeTrackingPersistenceUnit";

    /** Name of the {@link #entityManagerFactory} method */
    public static final String ENTITY_MANAGER_FACTORY_METHOD_NAME = "entityManagerFactory";

    /** Name of the {@link #transactionManager} method */
    public static final String TRANSACTION_MANAGER_METHOD_NAME = "transactionManager";

    @Value("${time-tracking.data.hibernate.dialect}")
    private String dialectProperty;

    @Value("${time-tracking.data.hibernate.show_sql}")
    private String showSqlProperty;

    @Value("${time-tracking.data.hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Value("${time-tracking.data.datasource.driver}")
    private String hibernateDriver;

    @Value("${time-tracking.data.datasource.jdbc-url}")
    private String jdbcUrl;

    @Value("${time-tracking.data.datasource.username}")
    private String username;

    @Value("${time-tracking.data.datasource.password}")
    private String password;

    @Value("${time-tracking.data.datasource.maximum-pool-size}")
    private Integer maxPoolSize;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setPackagesToScan(PackageNames.DATABASE_TIME_TRACKING_ENTITY);
        entityManagerFactory.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
        entityManagerFactory.setJpaProperties(createHibernateProperties(dialectProperty, showSqlProperty, hbm2ddlAuto));

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);
        return entityManagerFactory;
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName(hibernateDriver);
        hikariConfig.setJdbcUrl(jdbcUrl);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setDataSourceProperties(createBasicDataSourceProperties());
        hikariConfig.setMaximumPoolSize(maxPoolSize);
        HikariDataSource dataSource = new HikariDataSource(hikariConfig);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return jpaTransactionManager;
    }

    /* HELPER METHODS */

    private static Properties createBasicDataSourceProperties() {
        Properties properties = new Properties();
        properties.setProperty("cachePrepStmts", "true");
        properties.setProperty("prepStmtCacheSize", "250");
        properties.setProperty("prepStmtCacheSqlLimit", "2048");
        return properties;
    }

    private static Properties createHibernateProperties(String dialectProperty, String showSqlProperty, String hbm2ddlAuto) {
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(AvailableSettings.DIALECT, dialectProperty);
        hibernateProperties.setProperty(AvailableSettings.SHOW_SQL, showSqlProperty);
        hibernateProperties.setProperty(AvailableSettings.HBM2DDL_AUTO, hbm2ddlAuto);
        hibernateProperties.setProperty(AvailableSettings.NON_CONTEXTUAL_LOB_CREATION, "true");
        return hibernateProperties;
    }
}

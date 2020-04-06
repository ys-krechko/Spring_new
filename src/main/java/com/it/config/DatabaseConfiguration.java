package com.it.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Database configuration class
 */
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
        "com.it.repository"
})
public class DatabaseConfiguration {

    @Value("${connection.url}")
    private String url;

    @Value("${connection.username}")
    private String username;

    @Value("${connection.password}")
    private String password;

    @Value("${pool.name}")
    private String poolName;

    @Value("${connection.timeout}")
    private String connectionTimeout;

    @Value("${max.life.time}")
    private String maxLifeTime;

    @Value("${max.pool.size}")
    private String maxPoolSize;

    @Value("${dataSource.cachePrepStmts}")
    private String enableCachePrepStmts;

    @Value("${dataSource.prepStmtCacheSize}")
    private String prepStmtCacheSize;

    @Value("${dataSource.prepStmtCacheSqlLimit}")
    private String prepStmtCacheSqlLimit;

    @Value("${dataSource.useServerPrepStmts}")
    private String useServerPrepStmts;

    @Value("${dataSource.useLocalSessionState}")
    private String useLocalSessionState;

    @Value("${dataSource.rewriteBatchedStatements}")
    private String rewriteBatchedStatements;

    @Value("${dataSource.cacheResultSetMetadata}")
    private String cacheResultSetMetadata;

    @Value("${dataSource.cacheServerConfiguration}")
    private String cacheServerConfiguration;

    @Value("${dataSource.elideSetAutoCommits}")
    private String elideSetAutoCommits;

    @Value("${dataSource.maintainTimeStats}")
    private String maintainTimeStats;

    /**
     * Creates DataSource object that configures connection to the physical data source
     *
     * @return - DataSource object
     */
    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder.setType(EmbeddedDatabaseType.H2).addScript("/start.sql").build();
    }

/*
    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setConnectionTimeout(Long.parseLong(connectionTimeout));
        ds.setMaxLifetime(Long.parseLong(maxLifeTime));
        ds.setMaximumPoolSize(Integer.parseInt(maxPoolSize));
        ds.setPoolName(poolName);
        ds.addDataSourceProperty("cachePrepStmts", enableCachePrepStmts);
        ds.addDataSourceProperty("prepStmtCacheSize", prepStmtCacheSize);
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);
        ds.addDataSourceProperty("useServerPrepStmts", useServerPrepStmts);
        ds.addDataSourceProperty("useLocalSessionState", useLocalSessionState);
        ds.addDataSourceProperty("rewriteBatchedStatements", rewriteBatchedStatements);
        ds.addDataSourceProperty("cacheResultSetMetadata", cacheResultSetMetadata);
        ds.addDataSourceProperty("cacheServerConfiguration", cacheServerConfiguration);
        ds.addDataSourceProperty("elideSetAutoCommits", elideSetAutoCommits);
        ds.addDataSourceProperty("maintainTimeStats", maintainTimeStats);

        return ds;
    }
*/

    /**
     * Creates a JPA EntityManagerFactory
     *
     * @return - LocalContainerEntityManagerFactoryBean object
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(dataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan("com.it.model");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        localContainerEntityManagerFactoryBean.setJpaProperties(additionalProperties());
        return localContainerEntityManagerFactoryBean;
    }

    /**
     * Creates TransactionManager
     *
     * @param emf - EntityManagerFactory object
     * @return - PlatformTransactionManager object
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    /**
     * Creates settings for Hibernate
     *
     * @return - Properties object with settings for Hibernate
     */
    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        //properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.show_sql", "true");
        return properties;
    }
}
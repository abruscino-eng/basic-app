package br.com.pagseguro.basicapp.configuration.datasource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "br.com.pagseguro.basicapp.repository.postgres",
        entityManagerFactoryRef = "postgresEntityManager",
        transactionManagerRef = "postgresTransactionManager"
)
public class PostgresDatasourceConfiguration {

    public static final String POSTGRES_DRIVER_CLASS = "org.postgresql.Driver";
    private static final String POSTGRES_CONNECTION_URL = "jdbc:postgresql://%1$s:%2$s/%3$s";

    private final String dbURL;

    private final String dbPort;

    private final String dbName;

    private final String dbUser;

    private final String dbPwd;


    public PostgresDatasourceConfiguration(@Value("${basicapp.datasources.postgres.server.url}") String dbURL,
                                           @Value("${basicapp.datasources.postgres.server.port}") String dbPort,
                                           @Value("${basicapp.datasources.postgres.db.name}") String dbName,
                                           @Value("${basicapp.datasources.postgres.db.user}") String dbUser,
                                           @Value("${basicapp.datasources.postgres.db.password}") String dbPwd) {
        this.dbURL = dbURL;
        this.dbPort = dbPort;
        this.dbName = dbName;
        this.dbUser = dbUser;
        this.dbPwd = dbPwd;
    }

    String getDBConnection() {
        final Object[] args = {dbURL, dbPort, dbName};
        return POSTGRES_CONNECTION_URL.formatted(args);
    }


    @Bean
    public DataSource postgresDataSource() {
        final DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(POSTGRES_DRIVER_CLASS);
        ds.setUrl(getDBConnection());
        ds.setUsername(dbUser);
        ds.setPassword(dbPwd);
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean postgresEntityManager() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(postgresDataSource());
        em.setPackagesToScan(new String[]{"br.com.pagseguro.basicapp.model.postgres"});
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager postgresTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(postgresEntityManager().getObject());
        return transactionManager;
    }
}

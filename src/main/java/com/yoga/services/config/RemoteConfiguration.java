package com.yoga.services.config;

import com.yoga.services.controller.TestController;
import com.yoga.services.service.TestService;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.inject.Inject;
import javax.sql.DataSource;

import static com.yoga.services.util.SpringConfigurationUtils.stringFromEnvironmentOrIllegalArgumentException;

@Configuration
@Import(TomcatConfiguration.class)
public class RemoteConfiguration {

    @Inject
    StandardEnvironment env;


    @Bean
    DataSource testDB() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName("net.sourceforge.jtds.jdbc.Driver");
        dataSource.setJdbcUrl(stringFromEnvironmentOrIllegalArgumentException("sor.probationer.datasource.jdbcurl", env));
        dataSource.setUsername(stringFromEnvironmentOrIllegalArgumentException("sor.probationer.datasource.username", env));
        dataSource.setPassword(stringFromEnvironmentOrIllegalArgumentException("sor.probationer.datasource.password", env));
        dataSource.setConnectionTestQuery("SELECT 1");
        dataSource.setMaximumPoolSize(20);
        dataSource.setMinimumIdle(10);
        return dataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(testDB());
    }

    @Bean
    TestController testController() {
        return new TestController(testService());
    }

    @Bean
    TestService testService(){
        return new TestService();
    }

}

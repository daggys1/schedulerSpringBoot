package us.ne.state.services.config

import com.zaxxer.hikari.HikariDataSource
import groovy.sql.Sql
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.core.env.StandardEnvironment
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.client.RestTemplate
import us.ne.state.services.Exceptions.DefaultExceptionHandler
import us.ne.state.services.controller.JwtTokenGeneratorController
import us.ne.state.services.controller.ProbationerDemographicsController
import us.ne.state.services.service.JwtTokenProviderService
import us.ne.state.services.service.ProbationerDemographicsService
import us.ne.state.services.service.SwapiApiSample
import us.ne.state.services.service.TokenVerificationService

import javax.inject.Inject
import javax.sql.DataSource

import static us.ne.state.services.util.SpringConfigurationUtils.intFromEnvironmentOrIllegalArgumentException
import static us.ne.state.services.util.SpringConfigurationUtils.stringFromEnvironmentOrIllegalArgumentException

@Configuration
@Import(TomcatConfiguration)
class RemoteConfiguration {
    @Inject
    StandardEnvironment env

    // TODO properties to be externalized to a remote properties file or create a new module for IAC



    //TODO externalize to a property file
    @Bean
    DataSource probationerDataSource() {
        def dataSource = new HikariDataSource()
        dataSource.setDriverClassName('net.sourceforge.jtds.jdbc.Driver')
        dataSource.setJdbcUrl(stringFromEnvironmentOrIllegalArgumentException('sor.probationer.datasource.jdbcurl', env))
        dataSource.setUsername(stringFromEnvironmentOrIllegalArgumentException('sor.probationer.datasource.username', env))
        dataSource.setPassword(stringFromEnvironmentOrIllegalArgumentException('sor.probationer.datasource.password', env))
        dataSource.setConnectionTestQuery("SELECT 1")
        dataSource.setMaximumPoolSize(20)
        dataSource.setMinimumIdle(10)
        dataSource
    }

    @Bean
    JdbcTemplate jdbcTemplate() {
        new JdbcTemplate(probationerDataSource())
    }

    @Bean
    ProbationerDemographicsService probationerDemographics() {
        new ProbationerDemographicsService(jdbcTemplate(), sql())
    }

    @Bean
    Sql sql() {
        new Sql(probationerDataSource())
    }

    //TODO externalize the values to props
    @Bean
    JwtTokenProviderService jwtTokenProviderService() {
        def jwtSecret = 'supersecret'
        def jwtIssuerId = 'somestring'
        def jwtIssuer = 'someone'
        new JwtTokenProviderService(jwtIssuerId, jwtIssuer, jwtSecret)
    }

    @Bean
    JwtTokenGeneratorController jwtTokenGeneratorController() {
        new JwtTokenGeneratorController(jwtTokenProviderService())
    }


    @Bean
    SwapiApiSample sample() {
        new SwapiApiSample(restTemplate())
    }

    @Bean
    RestTemplate restTemplate() {
        new RestTemplate()
    }

    @Bean
    DefaultExceptionHandler defaultExceptionHandler() {
        new DefaultExceptionHandler()
    }

    // TODO look at this configuration style to get the props from file that is not in the classpath:
    /* @Bean
     static PropertyPlaceholderConfigurer ppc() throws IOException {
         PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer()
         Properties properties = new Properties()
         properties.setProperty()
         ppc.setProperties(properties)
     }*/

    /* @Bean
     JwtFilter jwtFilter() {
         new JwtFilter(stringFromEnvironmentOrIllegalArgumentException('jwtSecret',env),
                 stringFromEnvironmentOrIllegalArgumentException('jwtIssuer',env),
                 stringFromEnvironmentOrIllegalArgumentException('jwtIssuerId',env))
     }*/

    @Bean
    TokenVerificationService tokenVerificationService() {
        new TokenVerificationService(stringFromEnvironmentOrIllegalArgumentException('jwtSecret',env),
                stringFromEnvironmentOrIllegalArgumentException('jwtIssuer',env),
                stringFromEnvironmentOrIllegalArgumentException('jwtIssuerId',env))
    }



    @Bean
    ProbationerDemographicsController probationerDemographicsController() {
        new ProbationerDemographicsController(probationerDemographics(),tokenVerificationService())
    }
}
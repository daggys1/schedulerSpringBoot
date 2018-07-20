package us.ne.state.services.config

import com.zaxxer.hikari.HikariDataSource
import groovy.sql.Sql
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import us.ne.state.services.controller.JwtTokenGeneratorController
import us.ne.state.services.controller.ProbationerDemographicsController
import us.ne.state.services.service.JwtTokenProviderService
import us.ne.state.services.service.ProbationerDemographicsService
import us.ne.state.services.service.TokenVerificationService

import javax.sql.DataSource

@Configuration
class RemoteConfiguration {

    // TODO properties to be externalized to a remote properties file or create a new module for IAC
    @Bean
    TomcatServletWebServerFactory servletContainer() {
        def factory = new TomcatServletWebServerFactory()
        factory.setPort(8082)
        factory.setContextPath("/probationer")
        println("application started on port 8082 under context /probationer")
        factory
    }

    @Bean
    ProbationerDemographicsController probationerDemographicsController() {
        new ProbationerDemographicsController(probationerDemographics(),tokenVerificationService())
    }

    //TODO externalize to a property file
    @Bean
    DataSource probationerDataSource() {
        def dataSource = new HikariDataSource()
        dataSource.setDriverClassName('net.sourceforge.jtds.jdbc.Driver')
        dataSource.setJdbcUrl("jdbc:jtds:sqlserver://192.168.33.10/NSC_Npacs")
        dataSource.setUsername('npacs')
        dataSource.setPassword('npacs1shere')
        dataSource.setConnectionTestQuery("SELECT 1")
        dataSource.setMaximumPoolSize(20)  // Leave small; see Hikari documentation
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
        new JwtTokenProviderService(jwtIssuerId,jwtIssuer,jwtSecret)
    }

    @Bean
    JwtTokenGeneratorController jwtTokenGeneratorController() {
        new JwtTokenGeneratorController(jwtTokenProviderService())
    }


    //TODO externalize this to props
    @Bean
    TokenVerificationService tokenVerificationService() {
        def jwtSecret = 'supersecret'
        def jwtIssuer = 'someone'
        def jwtIssuerId = 'somestring'
        new TokenVerificationService(jwtSecret,jwtIssuer,jwtIssuerId)
    }
}
package com.yoga.services.config

import com.yoga.services.controller.AppointmentsController
import com.yoga.services.service.AppointmentsService
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WebApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication)
    }

    static void main(String[] args) throws Exception {
        SpringApplication.run(WebApplication.class, args)
    }

    /* @Bean
     ScheduledFileReader scheduledFileReader() {
         new ScheduledFileReader()
     }*/

    @Bean
    AppointmentsService appointmentsService() {
        new AppointmentsService()
    }

    @Bean
    AppointmentsController appointmentsController() {
        new AppointmentsController(appointmentsService())
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory()
        factory.setPort(Integer.valueOf(System.getProperty('PORT')))
        factory.setContextPath(System.getProperty('CONTEXT'))
        factory
    }
}


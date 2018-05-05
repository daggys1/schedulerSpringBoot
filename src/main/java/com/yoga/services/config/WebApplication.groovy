package com.yoga.services.config

import com.yoga.services.service.ScheduledFileReader
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.web.SpringBootServletInitializer
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WebApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class)
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(WebApplication.class, args)
    }

    @Bean
    ScheduledFileReader scheduledFileReader() {
        new ScheduledFileReader()
    }
}


package us.ne.state.services.config

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.core.env.StandardEnvironment
import org.springframework.stereotype.Component

import javax.inject.Inject

import static us.ne.state.services.util.SpringConfigurationUtils.intFromEnvironmentOrIllegalArgumentException
import static us.ne.state.services.util.SpringConfigurationUtils.intFromEnvironmentOrNull
import static us.ne.state.services.util.SpringConfigurationUtils.stringFromEnvironmentOrIllegalArgumentException

@Component
class TomcatConfiguration {

    @Inject
    StandardEnvironment env

    @Bean
    TomcatServletWebServerFactory servletContainer() {
        def factory = new TomcatServletWebServerFactory()
        factory.setPort(intFromEnvironmentOrNull('probationer.system.services.config.port', env) ?: 8443)
        factory.setContextPath(stringFromEnvironmentOrIllegalArgumentException('probationer.system.services.pross.config.context', env))
        println("application started on port ${intFromEnvironmentOrIllegalArgumentException('probationer.system.services.config.port', env)} " +
                "under context ${stringFromEnvironmentOrIllegalArgumentException('probationer.system.services.pross.config.context', env)}")
        factory
    }
}

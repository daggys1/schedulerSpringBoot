package com.yoga.services.util

import org.springframework.core.env.StandardEnvironment

import javax.inject.Inject

class SpringConfigurationUtils {


    @Inject
    StandardEnvironment environment

    static String stringFromEnvironmentOrIllegalArgumentException(String key, StandardEnvironment environment) {
        def value
        if (!environment.getProperty(key)) {
            throw new IllegalArgumentException("${key} is not set in the properties file")
        } else {
            value = environment.getProperty(key)
        }
        value
    }

    static Integer intFromEnvironmentOrIllegalArgumentException(String key, StandardEnvironment environment) {
        def value
        try {
            if (!environment.getProperty(key)) {
                throw new IllegalArgumentException("${key} is not set in the properties file")
            } else {
                value = Integer.parseInt(environment.getProperty(key))
            }
        } catch (final Exception e) {
            throw new IllegalArgumentException("${e.message}")
        }

        value
    }

    static Integer intFromEnvironmentOrNull(String key, StandardEnvironment environment){
        def value = null
        try {
            if (environment.getProperty(key) == null) {
            return value
            } else {
                value = Integer.parseInt(environment.getProperty(key))
            }
        } catch (final Exception e) {
            throw new IllegalArgumentException("${e.message}")
        }

        value as int
    }
}

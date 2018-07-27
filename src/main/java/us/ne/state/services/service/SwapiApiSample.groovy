package us.ne.state.services.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class SwapiApiSample {
    private RestTemplate restTemplate

    SwapiApiSample(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    def swapi() {
        def res
        try {
            res = restTemplate.getForObject('https://swapi.co/api/people/1', String)
        } catch (final Throwable t) {
            throw t
        }
        res
    }
}

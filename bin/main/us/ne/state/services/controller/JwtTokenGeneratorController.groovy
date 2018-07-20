package us.ne.state.services.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import us.ne.state.services.service.JwtTokenProviderService

@RestController
class JwtTokenGeneratorController {

    private JwtTokenProviderService jwtTokenProviderService

    JwtTokenGeneratorController(JwtTokenProviderService jwtTokenProviderService) {
        this.jwtTokenProviderService = jwtTokenProviderService
    }


    @RequestMapping(value = '/token', method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<Map<String,String>> jwt(@RequestParam(value = 'user', required = false) String user) {
        def token
        def map = [:]
        try {
            token = jwtTokenProviderService.token(user)
        } catch (final Throwable t){
            throw t
        }
        map.put('token',token)
        new ResponseEntity<Map<String,String>>(map,HttpStatus.OK)

    }
}

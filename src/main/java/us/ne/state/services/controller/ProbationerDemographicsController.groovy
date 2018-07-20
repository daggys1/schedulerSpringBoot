package us.ne.state.services.controller

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import us.ne.state.services.service.ProbationerDemographicsService
import us.ne.state.services.service.TokenVerificationService

import javax.servlet.http.HttpServletRequest

@RestController
class ProbationerDemographicsController {

    private ProbationerDemographicsService service
    private TokenVerificationService tokenVerificationService

    ProbationerDemographicsController(ProbationerDemographicsService service,
                                      TokenVerificationService tokenVerificationService) {
        if(null == service || null == tokenVerificationService)
            throw new IllegalArgumentException("cannot instantiate ${getClass()} with out providing required ${TokenVerificationService} and ${ProbationerDemographicsService}.")
        this.service = service
        this.tokenVerificationService = tokenVerificationService
    }

    @RequestMapping(value = '/p', method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    ResponseEntity<Map<String, String>> pro(@RequestParam(value = "firstName", required = false) String firstname,
                                            @RequestParam(value = "lastName", required = false) String lastName,
                                            @RequestParam(value = "ssn", required = false) String ssn, HttpServletRequest request) {


        tokenVerificationService.verifyJwtOrUnauthorizedUserException(request.getHeader('token'), request.getHeader('user'))
        def res
        try {
            res = service.demographics(firstname, lastName, ssn)
        } catch (final Exception e) {
            throw e
        }
        return new ResponseEntity<Map<String, String>>(res, HttpStatus.OK)
    }
}

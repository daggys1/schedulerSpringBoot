package us.ne.state.services.controller

import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification
import us.ne.state.services.service.ProbationerDemographicsService
import us.ne.state.services.service.TokenVerificationService

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

class ProbationerDemographicsControllerTest extends Specification {


    ProbationerDemographicsController controller
    def service = Mock(ProbationerDemographicsService)
    def tokenVerification = Mock(TokenVerificationService)
    MockMvc mockMvc

    def setup() {
        controller = new ProbationerDemographicsController(service, tokenVerification)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
    }

    def 'searchHsppyPath'(){
        def fn = 'a'
        def ln = 'Test'
        def ssn = '123'
        def url = "/p?lastName=$ln"
        List<Map<String, String>> search = new ArrayList()

        when: 'search api is called'

        def res = mockMvc.perform(get(url))
        then:
        1* tokenVerification.verifyJwtOrUnauthorizedUserException('somejwt','someuuser')
        1* service.search(fn,ln,ssn) >> search

        res.andExpect(status().isOk())
    }
}

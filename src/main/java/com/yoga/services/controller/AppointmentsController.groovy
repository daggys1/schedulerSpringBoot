package com.yoga.services.controller

import com.yoga.services.service.AppointmentsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AppointmentsController {

    AppointmentsService service

    AppointmentsController(AppointmentsService service) {
        this.service = service
    }

    @RequestMapping(name = '/appointments', method = RequestMethod.GET)
    ResponseEntity<Object> getAppointments(@RequestParam("name") String resourceId) {

        // do your authorization validation input and stiff!!
         /*if (isUserAuthorized()){
             throw new UnauthorizedUserException
         }*/

        // do your input validation and stuff!!
        if (resourceId == null) {
            new ResponseEntity<Object>("ResourceId cannot be null", HttpStatus.BAD_REQUEST)
        }


        def response

        try {
            response = service.retrieveAppointments()
        } catch (final Throwable t) {
            // do your exception handling
            throw t
        }

        if (resourceId.equalsIgnoreCase('yoga')) {
            new ResponseEntity<Object>(response."$resourceId", HttpStatus.OK)
        } else if (resourceId.equalsIgnoreCase('john')) {
            new ResponseEntity<Object>(response."$resourceId", HttpStatus.OK)
        }

    }


}

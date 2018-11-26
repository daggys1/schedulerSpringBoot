package com.yoga.services.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class DefaultExceptionHandler extends ResponseEntityExceptionHandler {
    static String ERROR = 'ERROR'

    // TODO implement logging accordingly
    @ExceptionHandler(ValidationException)
    ResponseEntity<Map<String, Map<String, String>>> validationException(ValidationException e) {
        def map = new HashMap(1)
        map.put(ERROR, ['Validation': e.message])
        new ResponseEntity<Map<String, Map<String, String>>>(map, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(UnauthorizedUserException)
    ResponseEntity<Map<String, Object>> unAuthorized(UnauthorizedUserException e) {
        def map = new HashMap(1)
        map.put(ERROR, ['Authorization': e.message])
        new ResponseEntity<Map<String, Map<String, String>>>(map, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(UserNotFoundException)
    ResponseEntity<Map<String, Object>> notFound(UserNotFoundException e) {
        def map = new HashMap(1)
        map.put(ERROR, ['Not_Found': e.message])
        new ResponseEntity<Map<String, Map<String, String>>>(map, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ResourceNotFoundException)
    ResponseEntity<Map<String, Object>> resourceNotFound(ResourceNotFoundException e) {
        def map = new HashMap(1)
        map.put(ERROR, ['ResourceNotFound': e.message])
        new ResponseEntity<Map<String, Map<String, String>>>(map, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(SorCommunicationException)
    ResponseEntity<Map<String, Object>> sorCom(SorCommunicationException e) {
        def map = new HashMap(1)
        map.put(ERROR, ['SOR_COM': e.message])
        new ResponseEntity<Map<String, Map<String, String>>>(map, HttpStatus.BAD_GATEWAY)
    }

    @ExceptionHandler(Exception)
    ResponseEntity<Map<String, Object>> exception(Exception e) {
        def map = new HashMap(1)
        map.put(ERROR, ['System': e.message])
        new ResponseEntity<Map<String, Map<String, String>>>(map, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

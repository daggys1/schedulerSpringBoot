package com.yoga.services.service

import com.google.common.collect.ImmutableMap
import org.springframework.stereotype.Service

@Service
class AppointmentsService {


    AppointmentsService() {
    }

    // do your SOR communication and return the required response
    Map<String, List> retrieveAppointments() {

        def appointmentsYoga = new ArrayList()
        Map yogaAppMap = new HashMap()
        yogaAppMap.put('1', 'dentist')
        yogaAppMap.put('2', 'medical')
        appointmentsYoga.add(yogaAppMap)
        def appointmentsJohn = new ArrayList()
        Map johnAppMap = new HashMap()
        johnAppMap.put('1', 'dentist')
        johnAppMap.put('2', 'medical')
        appointmentsJohn.add(yogaAppMap)

        def appointmentsForResource = ImmutableMap.builder()
        appointmentsForResource.put('yoga', appointmentsYoga)
        appointmentsForResource.put('john',appointmentsJohn).build()

    }
}

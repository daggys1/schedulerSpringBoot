package com.yoga.services.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduledFileReader {

    /**
     * Reads a file every 5secs and sends notifications if configured to SMTP ;). See {@link org.springframework.scheduling.annotation.Scheduled} for more variants of running scheduled jobs.
     * @see org.springframework.scheduling.support.CronSequenceGenerator for how the pattern {cron = '*\/5 * * * * ?'} is implemented
     * @throws FileNotFoundException
     * @throws IllegalArgumentException
     */
    @Scheduled(cron = '*/5 * * * * ?')//runs every 5 secs
    void readFile() throws FileNotFoundException, IllegalArgumentException {

        //location of the file hardcoded can be externalized
        new File('C:/logs/logfile.log').eachLine {
            l ->
                println(l)
                //do something with the line
                if (l.contains('call_status=500')) {
                    //trigger some alerts
                    sendAlert('something is burning')
                } else if (l.contains('call_status=400')) {
                    //trigger some alerts
                    sendAlert('it\'s the users fault!! can we fix it.....;)')
                } else if (l.contains('call_status=200')) {
                    //update the dashboard with 200's
                    sendAlert('call completed')
                }

        }
    }

    static def sendAlert(def email) {
        //send email daah! or attach to a grafana dashboard
    }
}

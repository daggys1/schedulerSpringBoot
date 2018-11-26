import ch.qos.logback.core.*
import ch.qos.logback.core.rolling.*

def LOG_PATH = "C:/logs/webapps/pass/"
def LOG_ARCHIVE = "${LOG_PATH}/webapp"
appender("Console-Appender", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd HH:mm:ss} - %-5level - %logger{35} - %msg%n"
    }
}
appender("File-Appender", FileAppender) {
    file = "${LOG_PATH}/application.log"
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd HH:mm:ss} - %-5level - %logger{35} - %msg%n"
        outputPatternAsHeader = true
    }
}
appender("RollingFile-Appender", RollingFileAppender) {
    file = "${LOG_PATH}/rollingfile.log"
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_ARCHIVE}/rollingfile.%d{yyyy-MM-dd}.log"
        maxHistory = 30
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd HH:mm:ss} - %-5level - %logger{35} - %msg%n"
    }
}
logger("us.test ", INFO, ["File-Appender"], false)
//helpful for development and testing ;)
if(System.getProperty('LogToConsole')){
    root(DEBUG, ['Console-Appender'])
}
root(INFO, ['File-Appender','Console-Appender'])


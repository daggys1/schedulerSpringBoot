import ch.qos.logback.core.*
import ch.qos.logback.core.rolling.*

def LOG_PATH = "/logs"
def LOG_ARCHIVE = "${LOG_PATH}/webapp"
appender("Console-Appender", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%msg%n"
    }
}
appender("File-Appender", FileAppender) {
    file = "${LOG_PATH}/application.log"
    encoder(PatternLayoutEncoder) {
        pattern = "%msg%n"
        outputPatternAsHeader = true
    }
}
appender("RollingFile-Appender", RollingFileAppender) {
    file = "${LOG_PATH}/rollingfile.log"
    rollingPolicy(TimeBasedRollingPolicy) {
        fileNamePattern = "${LOG_ARCHIVE}/application-%d{yyyy-MM-dd}.log"
        maxHistory = 30
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%msg%n"
    }
}
logger("com.yoga.services", INFO, ["Console-Appender", "File-Appender"], false)
root(DEBUG, ["Console-Appender"])

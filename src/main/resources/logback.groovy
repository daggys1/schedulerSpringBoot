import ch.qos.logback.classic.AsyncAppender
import static ch.qos.logback.classic.Level.INFO

def LOG_PATH = "C:/logs"
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
        fileNamePattern = "${LOG_ARCHIVE}/rollingfile.log%d{yyyy-MM-dd}.log"
        maxHistory = 30
    }
    encoder(PatternLayoutEncoder) {
        pattern = "%msg%n"
    }
}
appender("Async-Appender", AsyncAppender) {
    appenderRef("RollingFile-Appender")
}
logger("com.yoga.services", INFO, ["Console-Appender", "File-Appender", "Async-Appender"], false)
root(INFO, ["Console-Appender"])

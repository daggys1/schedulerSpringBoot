# Scheduler-SpringBoot
Runs a cron job UN*X style, currently configured to run for every 5 secs, requires some configuration to send email triggers and grafana dashboard
Used gradle as the build/dependency management tool

Running this locally:
Assuming that you already have you're Java environment setup and gradle locally, download the zip, extract and run /gradle clean build

then run java -jar -DFILE_NAME=C:/logs/application.log build\libs\schedulerSpringBoot-1.0-1.jar //i'm running on windows!

you should have a file in  this location C:/logs/logfile.log else FileNotFoundException

or Run gradle clean build bootRun 

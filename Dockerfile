FROM centos:latest
VOLUME /tmp
ADD build/libs/schedulerSpringBoot-1.0-1.jar app.jar
EXPOSE 8081
RUN bash -c 'touch /app.jar'
# RUN mkdir /logs/webapps
CMD ["java", "-DPORT=8081", "-DCONTEXT=/news", "-jar", "app.jar"]
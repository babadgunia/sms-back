FROM java:17
VOLUME /tmp
COPY target/sms.jar sms.jar
ENTRYPOINT ["java","-jar","/sms.jar"]
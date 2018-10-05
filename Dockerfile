FROM java:8
VOLUME /tmp
COPY target/sms.jar sms.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sms.jar"]
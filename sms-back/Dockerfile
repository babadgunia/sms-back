FROM java:8
VOLUME /tmp
COPY target/sms-back-1.0.jar sms.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/sms.jar"]
FROM eclipse-temurin:21-jdk

ADD build/libs/kata.backend.jar /
ENTRYPOINT ["java","-server","-Djava.security.egd=file:/dev/./urandom","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8085","-jar","/kata.backend.jar"]
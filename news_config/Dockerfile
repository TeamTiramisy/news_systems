FROM openjdk:17-jdk-alpine
COPY build/libs/*.jar ./news_config.jar
ENTRYPOINT ["java", "-jar", "./news_config.jar"]
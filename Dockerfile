FROM openjdk:17-jdk-alpine
COPY ./build/libs/*.jar ./news.jar
ENTRYPOINT ["java", "-jar", "./news.jar"]
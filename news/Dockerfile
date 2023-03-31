FROM openjdk:17-jdk-alpine
COPY build/libs/*.jar ./news.jar
ENTRYPOINT sleep 30 && java -jar ./news.jar
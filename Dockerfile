FROM openjdk:17-jdk
#EXPOSE 8080
ARG JAR_FILE=*.jar
COPY ./*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
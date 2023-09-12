FROM amazoncorretto:11
COPY target/*.jar app.jar
COPY src/main/resources/*.txt WidgetPattern.txt
ENTRYPOINT ["java","-jar","/app.jar"]
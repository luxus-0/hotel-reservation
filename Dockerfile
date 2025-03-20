FROM openjdk:21
WORKDIR /hotel
COPY target/hotel-reservation-0.0.1-SNAPSHOT.jar hotel-reservation-0.0.1-SNAPSHOT.jar
EXPOSE 8080
CMD ["java", "-jar", "hotel-reservation-0.0.1-SNAPSHOT.jar"]

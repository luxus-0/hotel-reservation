FROM openjdk:21-jdk
COPY ../../../target/hotel-reservation-3.3.3.jar /app/hotel-reservation.jar
WORKDIR /app
CMD ["java", "-jar", "hotel-reservation.jar"]
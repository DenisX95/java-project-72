FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY /app .
RUN chmod +x ./gradlew
RUN ./gradlew build
CMD ["./gradlew", "run"]

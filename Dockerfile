FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY /app .
RUN gradle installDist
CMD ./build/install/app/bin/app

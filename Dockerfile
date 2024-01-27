FROM eclipse-temurin:17-jdk
RUN apt update && apt install -y dos2unix

WORKDIR /app

COPY settings.gradle ./
COPY gradlew ./
COPY build.gradle ./
COPY gradle ./gradle
RUN dos2unix ./gradlew
RUN ./gradlew --refresh-dependencies

COPY src ./src

CMD ["./gradlew", "bootRun"]
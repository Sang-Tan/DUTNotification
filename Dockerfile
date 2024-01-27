FROM eclipse-temurin:17-jdk
RUN apt update && apt install -y dos2unix

WORKDIR /app

COPY gradle ./gradle
COPY settings.gradle ./
COPY gradlew ./
RUN chmod +x ./gradlew
COPY build.gradle ./
RUN dos2unix ./gradlew
RUN ./gradlew --refresh-dependencies

COPY src ./src

CMD ["./gradlew", "bootRun"]
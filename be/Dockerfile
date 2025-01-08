# etap 1 - budowanie .jar

FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /build/

COPY . /build/

RUN mvn clean install -DskipTests

# etap 2 - uruchamianie .jar (potrzebne env vars)

FROM eclipse-temurin:21-jre AS runner
WORKDIR /app

COPY --from=build /build/target/*.jar /app/kino.jar

ENV DB_ENDPOINT=${DB_ENDPOINT}
ENV DB_USER=${DB_USER}
ENV DB_PASSWORD=${DB_PASSWORD}

ENV PROFILE=${APP_PROFILE}

#CMD java -jar kino.jar --spring.active.profile=${PROFILE}
CMD ["java", "-jar", "kino.jar", "--spring.profiles.active=${PROFILE}"]
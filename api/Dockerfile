FROM openjdk:8 as builder
WORKDIR /application

COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY src src
RUN ./gradlew -Pprofile=dev build -x test
RUN mkdir -p build/libs/dependency && (cd build/libs/dependency; jar -xf ../*.jar)

# the second stage of our build will copy the extracted layers
FROM openjdk:8
VOLUME /tmp
ARG DEPENDENCY=/application/build/libs/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app

ENV port 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-cp", "app:app/lib/*", "com.jaewoo.srs.Application"]
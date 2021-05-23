FROM openjdk:8 AS builder
COPY ./api/gradlew .
COPY ./api/gradle gradle
COPY ./api/build.gradle.kts .
COPY ./api/settings.gradle.kts .
COPY ./api/src src
RUN chmod +x ./gradlew
RUN ./gradlew -Pprofile=dev bootJar
#RUN ./gradlew -Pprofile=dev unpack

FROM openjdk:8
WORKDIR /application
COPY --from=builder build/libs/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

COPY --from=builder BOOT-INF/lib /app/lib
COPY --from=builder META-INF /app/META-INF
COPY --from=builder BOOT-INF/classes /app

ENV port 8080
ENV spring.profiles.active dev

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.jaewoo.srs.Application"]
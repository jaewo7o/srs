# FROM openjdk:8 AS build
# COPY gradlew .
# COPY gradle gradle
# COPY build.gradle.kts .
# COPY settings.gradle.kts .
# COPY src src
# RUN chmod +x ./gradlew
# RUN ./gradlew -Pprofile=dev bootJar

# WORKDIR /application
# COPY . /application
# RUN --mount=type=cache,target=/root/.gradle ./gradlew clean build
# RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

# FROM openjdk:8
# ARG DEPENDENCY=build/dependency
# COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY ${DEPENDENCY}/META-INF /app/META-INF
# COPY ${DEPENDENCY}/BOOT-INF/classes /app

FROM openjdk:8 as builder
WORKDIR /application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
#RUN java -Djarmode=layertools -jar application.jar extract
RUN java -jar application.jar extract

# the second stage of our build will copy the extracted layers
FROM openjdk:8
WORKDIR /application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/application/ ./

ENV port 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-cp", "app:app/lib/*", "com.jaewoo.srs.Application"]
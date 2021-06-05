FROM openjdk:8 as builder

ARG SPRING_PROFILES_ACTIVE
RUN echo ${SPRING_PROFILES_ACTIVE}

COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY src src
RUN ./gradlew -Pprofile=${SPRING_PROFILES_ACTIVE} build -x test
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

# the second stage of our build will copy the extracted layers
FROM openjdk:8
VOLUME /tmp
ARG DEPENDENCY=build/dependency
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app


ENV port 8080
ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.jaewoo.srs.Application"]
#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]


# FROM openjdk:8
# VOLUME /tmp
# ARG DEPENDENCY=build/dependency
# COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY ${DEPENDENCY}/META-INF /app/META-INF
# COPY ${DEPENDENCY}/BOOT-INF/classes /app

# ENV port 8080
# ENV SPRING_PROFILES_ACTIVE=${SPRING_PROFILES_ACTIVE}

# ENTRYPOINT ["java", "-cp", "app:app/lib/*", "com.jaewoo.srs.ApplicationKt"]
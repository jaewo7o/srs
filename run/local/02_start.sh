cd ../../api

./gradlew clean build -x test

java -jar ./build/libs/srs-api-0.0.6-SNAPSHOT.jar &
cd ../../api

./gradlew clean build -x test

docker build -t srs-api -f ./Dockerfile .
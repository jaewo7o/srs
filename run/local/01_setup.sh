docker network create srs-network

#dirname=${PWD##*/}
#echo $dirname

docker run -d --rm \
      --name redis \
      -p 6379:6379 \
      --network srs-network \
      redis

docker run -d --rm \
      --name maria \
      --net srs-network \
      -p 3306:3306 \
      -v ~/dev/workspace/srs/data/maria/conf.d:/etc/mysql/conf.d \
      -v ~/dev/workspace/srs/data/maria/data:/var/lib/mysql \
      -v ~/dev/workspace/srs/data/maria/initdb.d:/docker-entrypoint-initdb.d \
      -e TZ=Asia/Seoul \
      -e MYSQL_HOST=localhost \
      -e MYSQL_PORT=3306 \
      -e MYSQL_ROOT_PASSWORD=mariadb \
      -e MYSQL_DATABASE=srs \
      -e MYSQL_USER=srs \
      -e MYSQL_PASSWORD=srs123!! \
      mariadb:10

docker run -d --rm \
      --name mongo \
      --net srs-network \
      -p 27017:27017 \
      -v ~/dev/workspace/srs/data/mongo/:/data/db \
      mongo
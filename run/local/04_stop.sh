pid=`ps -ef | grep /build/libs/srs-api | grep -v grep | awk '{print $2}'`
kill -9 $pid

docker stop maria mongo redis
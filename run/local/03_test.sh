curl --header "Content-Type: application/json" \
    --request POST \
    --data '{"groupCode":"code1", "groupCodeNameKo":"groupCodeNameKo", "groupCodeNameEn":"groupCodeNameEn"}' \
http://localhost:8080/api/anonymous/group-codes

curl http://localhost:8080/api/anonymous/group-codes/code1

curl -X "DELETE" http://localhost:8080/api/anonymous/group-codes/code1

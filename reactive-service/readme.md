swagger URI: http://localhost:8189/webjars/swagger-ui/index.html

docker build -t hw40-reactive-service:v1 .
docker container run -p 8189:8189 -d hw40-reactive-service:v1
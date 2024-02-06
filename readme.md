swagger URI: http://localhost:8190/swagger-ui/index.html

docker build -t hw40-slow-service:v1 .
docker container run -p 8190:8190 -d hw40-slow-service:v1
@echo off
set RABBIT_ADDRESSES=rabbitmq-server:5672
set STORAGE_TYPE=mysql
set MYSQL_USER=zipkin
set MYSQL_PASS=zipkin
java -jar ./zipkin-server-2.20.0-exec.jar
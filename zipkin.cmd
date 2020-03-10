@echo off
set RABBIT_ADDRESSES=rabbitmq83-server:5672
set STORAGE_TYPE=mysql
set MYSQL_USER=zipkin
set MYSQL_PASS=Andrea_1204
java -jar ./zipkin-server-2.20.0-exec.jar
set BASURA=Esta es una variable requerida
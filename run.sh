#!/bin/bash
ports=(8200,8201)

#mvn clean install
scp /local/server.sh int@10.92.32.99:/sdb/jianfeng/server.sh
ssh int@10.92.32.99 "/sdb/jianfeng/server.sh"

#java -jar business/hello/target/hello-0.0.1-SNAPSHOT.jar --server.port=8200 >/dev/null 2>&1 &
#java -jar business/hello/target/hello-0.0.1-SNAPSHOT.jar --server.port=8201 >/dev/null 2>&1 &

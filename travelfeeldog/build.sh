#!/bin/bash
echo "start Build!"
./gradlew build
PID="TravelFeelDog"
echo $PID kill
kill -9 $(ps -ef | grep $PID | grep -v grep | awk '{print $2 }')
echo "start Application!"
nohup java -jar ./build/libs/TravelFeelDog.jar &
echo "Running Application"
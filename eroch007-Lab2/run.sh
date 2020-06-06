#!/bin/bash

time cp AREAWATER.csv output.txt

hadoop jar target/*_lab2-1.0-SNAPSHOT.jar file://`pwd`/AREAWATER.csv file://`pwd`/AREAWATER.txt

hadoop jar target/*_lab2-1.0-SNAPSHOT.jar file://`pwd`/AREAWATER.csv hdfs:///AREAWATER_Copy.csv

hadoop jar target/*_lab2-1.0-SNAPSHOT.jar hdfs:///AREAWATER_Copy.csv file://`pwd`/output1.txt

hadoop jar target/*_lab2-1.0-SNAPSHOT.jar hdfs:///AREAWATER_Copy.csv hdfs:///AREAWATER_COPY2.csv

#!/bin/bash

mvn clean package > mvnpackage_output.txt

spark-submit target/*.jar kc_house_data.csv regression

spark-submit target/*.jar sentiment.csv classification

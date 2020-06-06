#!/bin/bash

mvn clean package

spark-submit --class edu.ucr.cs.cs167.eroch007.Filter --master local[2] target/*.jar nasa_19950630.22-19950728.12.tsv filter_output 302
spark-submit --class edu.ucr.cs.cs167.eroch007.Aggregation --master local[2] target/*.jar  nasa_19950630.22-19950728.12.tsv aggregation_output
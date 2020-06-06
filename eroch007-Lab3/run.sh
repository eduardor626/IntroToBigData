#!/bin/bash

mvn clean package 
yarn jar target/*.jar edu.ucr.cs.cs167.eroch007.Filter nasa_19950630.22-19950728.12.tsv filter_output 200
yarn jar target/*.jar edu.ucr.cs.cs167.eroch007.Aggregation nasa_19950630.22-19950728.12.tsv aggregation_output
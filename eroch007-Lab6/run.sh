#!/usr/bin/env sh

mvn clean package  

#count-all
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab6-1.0-SNAPSHOT.jar count-all nasa_19950630.22-19950728.12.tsv >> output.txt
#code-filter	
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab6-1.0-SNAPSHOT.jar code-filter nasa_19950630.22-19950728.12.tsv 302 >> output.txt
#time-filter
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab6-1.0-SNAPSHOT.jar time-filter nasa_19950630.22-19950728.12.tsv 804955673 805590159 >> output.txt
#count-by-code
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab6-1.0-SNAPSHOT.jar count-by-code nasa_19950630.22-19950728.12.tsv >> output.txt
#sum-bytes-by-code	
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab6-1.0-SNAPSHOT.jar sum-bytes-by-code nasa_19950630.22-19950728.12.tsv >> output.txt
#avg-bytes-by-code
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab6-1.0-SNAPSHOT.jar avg-bytes-by-code nasa_19950630.22-19950728.12.tsv >> output.txt
#top-host	
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab6-1.0-SNAPSHOT.jar top-host nasa_19950630.22-19950728.12.tsv >> output.txt
#comparison
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab6-1.0-SNAPSHOT.jar comparison nasa_19950630.22-19950728.12.tsv 805383872 >> output.txt

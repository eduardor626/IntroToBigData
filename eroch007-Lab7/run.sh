#!/usr/bin/env sh

mvn clean package > mvnpackage_output.txt

#create non - partitioned parque
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar write-parquet Crimes_-_2001_to_present.csv Chicago_Crimes.parquet    
#create a partitioned parque
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar write-parquet-partitioned Crimes_-_2001_to_present.csv Chicago_Crimes_Partition.parquet    


#csv
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar top-crime-types Crimes_-_2001_to_present.csv 
#non-partitioned
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar top-crime-types Chicago_Crimes.parquet
#partitioned
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar top-crime-types Chicago_Crimes_Partition.parquet



#csv
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar find Crimes_-_2001_to_present.csv HY413923 
#non-partitioned
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar find Chicago_Crimes.parquet HY413923 
#partitioned
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar find Chicago_Crimes_Partition.parquet HY413923 



#csv
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar stats Crimes_-_2001_to_present.csv 
#non-partition
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar stats Chicago_Crimes.parquet   
#partition  
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar stats Chicago_Crimes_Partition.parquet  

#csv
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar stats-district Crimes_-_2001_to_present.csv 8  
#non-partition
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar stats-district Chicago_Crimes.parquet 8 
#partition
spark-submit --class edu.ucr.cs.cs167.eroch007.App target/eroch007_lab7-1.0-SNAPSHOT.jar stats-district Chicago_Crimes_Partition.parquet 8 
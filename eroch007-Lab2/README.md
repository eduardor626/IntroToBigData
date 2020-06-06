# Lab 2

## Student information
* Full name: Eduardo Rocha
* E-mail: eroch007@ucr.edu
* UCR NetID: eroch007
* Student ID: 862086543

## Answers

* (Q1) Verify the file size and record the running time.

   - Copied 2271210910 bytes from  AREAWATER.csv to output.txt in 7.870234733 seconds. 


* (Q2) Record the running time of the copy command.
   - Command: time cat cp AREAWATER.csv output.txt  
   		output: 0.01s user 1.82s system 31% cpu 5.767 total.

   		Command: time cp AREAWATER.csv output.txt
   	 	real	0m5.290s
	 	user	0m0.010s
	 	sys	0m1.793s

* (Q3) How do the two numbers compare? (The running times of copying the file through your program and the operating system.) Explain _in your own words_ why you see these results.
   - My program was a bit slower maybe due to the fact that it is ran through intelliJ. Also since it is written in Java it is passed through an interpreter, causing it to be slower. I'm assuming also that the built in cp function is really optimized.

* (Q4) Does the program run after you change the default file system to HDFS? What is the error message, if any, that you get?
   - The program does not run once I chage the default file system to HDFS. The error message that is displayed on the command line is:
```
Exception in thread "main" java.io.FileNotFoundException: No file namedAREAWATER.csv

	at edu.ucr.cs.cs167.eroch007.App.main(App.java:54)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.apache.hadoop.util.RunJar.run(RunJar.java:244)
	at org.apache.hadoop.util.RunJar.main(RunJar.java:158)
   ```
   I get an error that the file cannot be found. Maybe because HDFS system is stateless, meaning it does not know where it is.

* (Q5) Use your program to test the following cases and record the running time for each case.
	- Copy a file from local file system to HDFS

```
hadoop jar target/eroch007_lab2-1.0-SNAPSHOT.jar 
file:///Users/eduardorocha/workspace/eroch007_lab2/AREAWATER.csv 
hdfs:///Users/eduardorocha/workspace/eroch007_lab2/AREAWATER_Copy.csv

Copied 2271210910 bytes from  file:/Users/eduardorocha/workspace/eroch007_lab2/AREAWATER.csv to hdfs:/Users/eduardorocha/workspace/eroch007_lab2/AREAWATER_Copy.csv in 9.537457274 seconds
```
- Copy a file from HDFS to local file system.
   Here is the output to a .txt file
```
hadoop jar target/eroch007_lab2-1.0-SNAPSHOT.jar 
hdfs:///user/eduardo/AREAWATER_Copy.csv 
file:///Users/eduardorocha/workspace/eroch007_lab2/output.txt

Copied 2271210910 bytes from  hdfs:/user/eduardo/AREAWATER_Copy.csv to file:/Users/eduardorocha/workspace/eroch007_lab2/output.txt in 9.597900586 seconds
```
   - Copy a file from HDFS to HDFS.
```
hadoop jar target/eroch007_lab2-1.0-SNAPSHOT.jar 
hdfs:///user/eduardo/AREAWATER_COPY.csv 
hdfs:///user/eduardo/AREAWATER_COPY2.csv

Copied 2271210910 bytes from  hdfs:/AREAWATER_Copy.csv to hdfs:/AREAWATER_COPY2.csv in 9.792022553 seconds
```
After running my program in the run.sh i get these final times

| Command       | Run Time (sec)          |
| ------------- |:-------------:|
|  cp AREAWATER.csv output.txt  | 7.093  |
|  local to local      | 6.871      |  
|  local  to hdfs | 7.948
|  hdfs to local | 9.883   |  
|  hdfs to hdfs | 9.792   |  


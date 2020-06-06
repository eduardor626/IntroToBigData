# Lab 1

## Student information
* Full name: Eduardo Rocha
* E-mail: eroch007@ucr.edu
* UCR NetID: eroch007
* Student ID: 862086543

## Answers

* (Q1) What is the name of the created directory?
   - eroch007_lab1

* (Q2) What do you see at the console output?

   - Hello World!

* (Q3) What do you see at the output?
   
   - We see an out of bounds exception because there is no arguments being passed into the program.   
   ```
   WARNING: An illegal reflective access operation has occurred
  WARNING: Illegal reflective access by org.apache.hadoop.security.authentication.util.KerberosUtil (file:/Users/eduardorocha/.m2/repository/org/apache/hadoop/hadoop-auth/2.10.0/hadoop-auth-2.10.0.jar) to method sun.security.krb5.Config.getInstance()
  WARNING: Please consider reporting this to the maintainers of org.apache.hadoop.security.authentication.util.KerberosUtil
  WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
  WARNING: All illegal access operations will be denied in a future release
  Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0
   at edu.ucr.cs167.eroch007.App.main(App.java:56)
  ```
  
* (Q4) What is the output that you see at the console?

    - A output.txt directory is created and one of them is ‘part-r-00000’.
   This contains the word count from input.txt file.

   ```
   but   1
   cannot   3
   crawl 1
   do 1
   fly,  1
   forward  1
   have  1
   if 3
   keep  1
   moving   1
   run   1
   run,  1
   then  3
   to 1
   walk  1
   walk, 1
   whatever 1
   you   5
   ```
   Console Output:
   ```
  console
    20/04/03 13:06:46 DEBUG security.UserGroupInformation:PrivilegedAction as:eduardorocha (auth:SIMPLE)from:org.apache.hadoop.mapreduce.Job.updateStatus(Job.java:328)20/04/03 13:06:46 DEBUG util.ShutdownHookManager: Completed shutdown in 0.007 seconds; Timeouts: 0
   20/04/03 13:06:46 DEBUG util.ShutdownHookManager: ShutdownHookManger completed shutdown.
   Process finished with exit code 0
  ```

* (Q5) Does it run? Why or why not?

   - It does not run because we have just built the .jar folder in the target directory. But not have specifically
told the program to run. Also the file output.txt is still in the repository. So it cannot create one because it already
exists.
   ```
   File Output Format Counters 
      Bytes Written=142
    20/04/03 21:30:03 INFO mapred.LocalJobRunner: Finishing task: attempt_local1692330653_0001_r_000000_0
    20/04/03 21:30:03 INFO mapred.LocalJobRunner: reduce task executor complete.
    20/04/03 21:30:04 INFO mapreduce.Job: Job job_local1692330653_0001 running in uber mode : false
    20/04/03 21:30:04 INFO mapreduce.Job:  map 100% reduce 100%
    20/04/03 21:30:04 INFO mapreduce.Job: Job job_local1692330653_0001 completed successfully
    20/04/03 21:30:04 INFO mapreduce.Job: Counters: 30
   File System Counters
      FILE: Number of bytes read=12498
      FILE: Number of bytes written=1007078
      FILE: Number of read operations=0
      FILE: Number of large read operations=0
      FILE: Number of write operations=0
   Map-Reduce Framework
      Map input records=4
      Map output records=28
      Map output bytes=252
      Map output materialized bytes=208
      Input split bytes=123
      Combine input records=28
      Combine output records=18
      Reduce input groups=18
      Reduce shuffle bytes=208
      Reduce input records=18
      Reduce output records=18
      Spilled Records=36
      Shuffled Maps =1
      Failed Shuffles=0
      Merged Map outputs=1
      GC time elapsed (ms)=9
      Total committed heap usage (bytes)=468713472
   Shuffle Errors
      BAD_ID=0
      CONNECTION=0
      IO_ERROR=0
      WRONG_LENGTH=0
      WRONG_MAP=0
      WRONG_REDUCE=0
   File Input Format Counters 
      Bytes Read=139
   File Output Format Counters 
      Bytes Written=142
   ```
This is my final output when i run the script file. 

